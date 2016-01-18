package com.teamdev;

import com.google.gson.*;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.LogInDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class UserTest extends ConfigData {

    @Test
    public void testGetUserData() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserDto userDto = new UserDto(0, NAME, MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, NAME);
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomDto chatRoomDto = new ChatRoomDto(0, "myRoom");
        json = gson.toJson(chatRoomDto);
        String URL_CREATE_ROOM = URL + "/createchat/" + userId + "?token=" + token;
        createRoom(json, httpClient, URL_CREATE_ROOM);

        try {
            HttpGet request = new HttpGet(URL + "/user/" + userId + "?token=" + token);
            HttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for get user data", 200, code);

            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonObject jobject = jelement.getAsJsonObject();
            String name = jobject.get("name").getAsString();
            String mail = jobject.get("mail").getAsString();

            assertEquals("User name is incorrect", NAME, name);
            assertEquals("Mail is incorrect", MAIL, mail);
        } catch (IOException e) {
            fail("IOException was appeared");
        }

        String URL_CLEAN_DB = URL + "/delete/" + userId + "?token=" + token;

        cleanDb(URL_CLEAN_DB, httpClient);
    }

    @Test
    public void testGetUserChats() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserDto userDto = new UserDto(0, NAME, MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, NAME);
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomDto chatRoomDto = new ChatRoomDto(0, "myRoom");
        json = gson.toJson(chatRoomDto);
        String URL_CREATE_ROOM = URL + "/createchat/" + userId + "?token=" + token;
        String roomId = createRoom(json, httpClient, URL_CREATE_ROOM);

        try {
            HttpGet request = new HttpGet(URL + "/join/" + roomId + "/" + userId + "?token=" + token);
            HttpResponse response = httpClient.execute(request);
        } catch (IOException e) {
            fail("IOException was appeared");
        }

        try {
            HttpGet request = new HttpGet(URL + "/userchats/" + userId + "?token=" + token);
            HttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for get user data", 200, code);

            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonArray jsonArray = jelement.getAsJsonArray();

            String roomName = "";
            assertNotEquals("Count of rooms is incorrect", 0, jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement jsonElement = jsonArray.get(i);
                JsonObject jobject = jsonElement.getAsJsonObject();
                roomName = jobject.get("roomName").getAsString();
                assertEquals("Room name is incorrect", "myRoom", roomName);
            }

        } catch (IOException e) {
            fail("IOException was appeared");
        }

        String URL_CLEAN_DB = URL + "/delete/" + userId + "?token=" + token;

        cleanDb(URL_CLEAN_DB, httpClient);
    }

}
