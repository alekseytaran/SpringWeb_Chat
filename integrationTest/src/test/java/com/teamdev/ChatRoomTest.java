package com.teamdev;

import com.google.gson.*;
import com.teamdev.requestDto.ChatRoomRequestDto;
import com.teamdev.requestDto.UserRequestDto;
import com.teamdev.requestDto.LogInDto;
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

public class ChatRoomTest extends ConfigData {

    public final String MAIL = "vasya@gemail.com";
    public final String PASSWORD = "qwerty";

    @Test
    public void testCreateChatRoom() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("alex", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "alex");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        try {
            ChatRoomRequestDto chatRoomRequestDto = new ChatRoomRequestDto("roomName");
            json = gson.toJson(chatRoomRequestDto);
            HttpPost request = new HttpPost(URL + "/chat/" + userId + "?token=" + token);
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
            String result = jobject.get("chatRoomId").getAsString();

            assertNotEquals("Chat room id equals 0 ", 0, result);
        } catch (IOException e) {
            fail("IOException was appeared");
        }

    }

    @Test
    public void testFindAllChats() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("alex1", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "alex1");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomRequestDto chatRoomRequestDto = new ChatRoomRequestDto("myRoom");
        json = gson.toJson(chatRoomRequestDto);
        String URL_CREATE_ROOM = URL + "/chat/" + userId + "?token=" + token;
        createRoom(json, httpClient, URL_CREATE_ROOM);

        try {
            HttpGet request = new HttpGet(URL + "/chats/" + userId + "?token=" + token);
            HttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for get user data", 200, code);

            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonArray jsonArray = jelement.getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement jsonElement = jsonArray.get(i);
                JsonObject jobject = jsonElement.getAsJsonObject();
                String result = jobject.get("roomName").getAsString();
                assertEquals("Room name is incorrect", "myRoom", result);
            }

        } catch (IOException e) {
            fail("IOException was appeared");
        }

    }

    @Test
    public void testJoinUserToChat() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("alex2", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "alex2");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomRequestDto chatRoomRequestDto = new ChatRoomRequestDto("myRoom");
        json = gson.toJson(chatRoomRequestDto);
        String URL_CREATE_ROOM = URL + "/chat/" + userId + "?token=" + token;
        String roomId = createRoom(json, httpClient, URL_CREATE_ROOM);

        try {
            HttpPost request = new HttpPost(URL + "/join/" + roomId + "/" + userId + "?token=" + token);
            HttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for join user to chat", 200, code);

            HttpGet requestGet = new HttpGet(URL + "/user/chats/" + userId + "?token=" + token);
            response = httpClient.execute(requestGet);
            code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for get user data", 200, code);
            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonArray jsonArray = jelement.getAsJsonArray();

            assertNotEquals("Count of rooms is incorrect", 0, jsonArray.size());
        } catch (IOException e) {
            fail("IOException was appeared");
        }

    }

    @Test
    public void testGetDataUserInChat() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("alex3", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "alex3");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomRequestDto chatRoomRequestDto = new ChatRoomRequestDto("myRoom");
        json = gson.toJson(chatRoomRequestDto);
        String URL_CREATE_ROOM = URL + "/chat/" + userId + "?token=" + token;
        String roomId = createRoom(json, httpClient, URL_CREATE_ROOM);

        String URL_JOIN_USER_TO_CHAT = URL + "/join/" + roomId + "/" + userId + "?token=" + token;
        joinUserToChat(httpClient, URL_JOIN_USER_TO_CHAT);

        try {
            HttpGet request = new HttpGet(URL + "/users/" + roomId + "/" + userId + "?token=" + token);
            HttpResponse response = httpClient.execute(request);

            int code = response.getStatusLine().getStatusCode();

            assertEquals("Response code is incorrect for join user to chat", 200, code);

            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonArray jsonArray = jelement.getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement jsonElement = jsonArray.get(i);
                JsonObject jobject = jsonElement.getAsJsonObject();
                String name = jobject.get("name").getAsString();
                String email = jobject.get("email").getAsString();
                assertEquals("Room name is incorrect", "alex3", name);
                assertEquals("Room name is incorrect", MAIL, email);
            }
        } catch (IOException e) {
            fail("IOException was appeared");
        }

    }
}
