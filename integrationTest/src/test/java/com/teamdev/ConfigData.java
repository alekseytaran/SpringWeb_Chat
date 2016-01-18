package com.teamdev;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamdev.requestDto.LogInDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import javax.xml.ws.Response;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class ConfigData {

    public final String NAME = "vasya";
    public final String MAIL = "vasya@gemail.com";
    public final String PASSWORD = "qwerty";

    public final String QUERY = "http://";
    public final String SERVER = "localhost:";
    public final String PORT = "8080";
    public final String CONTEXT = "/chats";
    public final String CHAT = "/chat";

    public final String URL = QUERY + SERVER + PORT + CONTEXT + CHAT;
    public final String URL_SIGN_UP = URL + "/signup";
    public final String URL_LOG_IN = URL + "/login";

    public static String signUp(String json, CloseableHttpClient httpClient, String URL) {

        try {
            HttpPost request = new HttpPost(URL);
            request.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(request);

            response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonObject jobject = jelement.getAsJsonObject();
            String result = jobject.get("userId").getAsString();

            return result;

        } catch (IOException e) {
            fail("IOException during request for sign up user");
        }
        return "";
    }

    public static String logIn(String json, CloseableHttpClient httpClient, String URL) {
        try {
            HttpPost request = new HttpPost(URL);
            request.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(request);

            response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonObject jobject = jelement.getAsJsonObject();

            return jobject.get("accessToken").getAsString();
        } catch (IOException e) {
            fail("IOException during request for log in user");
        }

        return "";
    }

    public static String createRoom(String json, CloseableHttpClient httpClient, String URL) {
        try {
            HttpPost request = new HttpPost(URL);
            request.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(request);

            response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonObject jobject = jelement.getAsJsonObject();

            return jobject.get("chatRoomId").getAsString();
        } catch (IOException e) {
            fail("IOException during request for create room");
        }

        return "";
    }

    public static void joinUserToChat(CloseableHttpClient httpClient, String URL) {
        try {
            HttpGet request = new HttpGet(URL);
            httpClient.execute(request);
        } catch (IOException e) {
            fail("IOException during request for sign up user");
        }

    }

    public static void cleanDb(String URL, CloseableHttpClient httpClient) {
        try {
            HttpGet request = new HttpGet(URL);
            HttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();

            assertEquals("BD wasn't clean after test", 200, code);
        } catch (IOException e) {
            fail("IOException during request for clean db");
        }
    }
}
