package com.teamdev;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamdev.requestDto.LogInDto;
import com.teamdev.requestDto.UserRequestDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AuthenticationTest extends ConfigData {

    public final String MAIL = "vasya@gemail.com";
    public final String PASSWORD = "qwerty";

    @Test
    public void testSignUpAndLogIn() {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("vasya", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);

        String userId = "";
        String token = "";

        try {
            HttpPost request = new HttpPost(URL + "/signup");
            request.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(request);

            int code = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonObject jobject = jelement.getAsJsonObject();
            userId = jobject.get("userId").getAsString();

            assertEquals("Response code is incorrect for sign up", 200, code);
            assertNotEquals("Sign up is fail", 0,  Integer.parseInt(userId));
        } catch (IOException e) {
            fail("IOException during request for sign up user");
        }

        try {
            LogInDto logInDto = new LogInDto(PASSWORD, "vasya");
            json = gson.toJson(logInDto);
            HttpPost request = new HttpPost(URL + "/login");
            request.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(request);

            int code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for log in", 200, code);

            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonObject jobject = jelement.getAsJsonObject();
            token = jobject.get("accessToken").getAsString();

            assertEquals("Log in is fail", PASSWORD+"vasya",  token);
        } catch (IOException e) {
            fail("IOException during request for log in user");
        }
    }

    @Test
    public void testFailSignUp() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("vasya1", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);

        String URL_SIGN_UP = URL + "/signup";

        String userId = signUp(json, httpClient, URL_SIGN_UP);

        try {
            HttpPost request = new HttpPost(URL_SIGN_UP);
            request.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            assertEquals("Status code is incorrect", 403, statusCode);
            assertEquals("Exception message is incorrect", "user with this credentials has already existed in DB", responseString);
        } catch (IOException e) {
            fail("IOException was appeared");
        }

    }

    @Test
    public void testLogInFail() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        Gson gson = new Gson();

        try {
            LogInDto logInDto = new LogInDto(PASSWORD, "invalidName");
            String json = gson.toJson(logInDto);
            HttpPost request = new HttpPost(URL + "/login");
            request.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            int code = response.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            assertEquals("Response code is incorrect for log in", 403, code);
            assertEquals("Exception message is incorrect", "User with current credentials not found", responseString);

        } catch (IOException e) {
            fail("IOException during request for log in user");
        }
    }

    @Test
    public void testCheckToken() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("vasya2", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "vasya2");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        try {
            HttpGet request = new HttpGet(URL + "/check/" + userId + "?token=" + token);
            HttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for get user data", 200, code);
        } catch (IOException e) {
            fail("IOException was appeared");
        }

    }

    @Test
    public void testLogOut() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("vasya3", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "vasya3");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        try {
            HttpGet request = new HttpGet(URL + "/logout/" + userId + "?token=" + token);
            HttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for get user data", 200, code);
        } catch (IOException e) {
            fail("IOException was appeared");
        }
    }

}
