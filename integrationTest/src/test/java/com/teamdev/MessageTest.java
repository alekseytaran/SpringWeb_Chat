package com.teamdev;

import com.google.gson.*;
import com.teamdev.requestDto.ChatRoomRequestDto;
import com.teamdev.requestDto.LogInDto;
import com.teamdev.requestDto.MessageRequestDto;
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
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class MessageTest extends ConfigData{

    public final String MAIL = "vasya@gemail.com";
    public final String PASSWORD = "qwerty";

    @Test
    public void testSendMessage() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("petya2", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "petya2");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomRequestDto chatRoomRequestDto = new ChatRoomRequestDto("myRoom");
        json = gson.toJson(chatRoomRequestDto);
        String URL_CREATE_ROOM = URL + "/chat/" + userId + "?token=" + token;
        String roomId = createRoom(json, httpClient, URL_CREATE_ROOM);

        String URL_JOIN_USER_TO_CHAT = URL + "/join/" + roomId + "/" + userId + "?token=" + token;
        joinUserToChat(httpClient, URL_JOIN_USER_TO_CHAT);

        try {
            MessageRequestDto messageRequestDto = new MessageRequestDto("hello chat", Long.parseLong(userId), Long.parseLong(roomId), new Date(System.currentTimeMillis()));
            json = gson.toJson(messageRequestDto);
            HttpPost request = new HttpPost(URL + "/message/" + userId + "?token=" + token);
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
            String result = jobject.get("messageId").getAsString();

            assertNotEquals("Chat room id equals 0 ", 0, result);
        } catch (IOException e) {
            fail("IOException was appeared");
        }
    }

    @Test
    public void testSendPrivateMessage() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("petya3", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        UserRequestDto recipientRequestDto = new UserRequestDto("petya4", MAIL, PASSWORD);
        gson = new Gson();
        json = gson.toJson(recipientRequestDto);
        String recipientId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "petya3");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomRequestDto chatRoomRequestDto = new ChatRoomRequestDto("myRoom");
        json = gson.toJson(chatRoomRequestDto);
        String URL_CREATE_ROOM = URL + "/chat/" + userId + "?token=" + token;
        String roomId = createRoom(json, httpClient, URL_CREATE_ROOM);

        String URL_JOIN_USER_TO_CHAT = URL + "/join/" + roomId + "/" + userId + "?token=" + token;
        joinUserToChat(httpClient, URL_JOIN_USER_TO_CHAT);

        try {
            MessageRequestDto messageRequestDto = new MessageRequestDto("hello chat", Long.parseLong(userId), Long.parseLong(roomId), new Date(System.currentTimeMillis()));
            json = gson.toJson(messageRequestDto);
            HttpPost request = new HttpPost(URL + "/message/" + userId + "/" + recipientId + "?token=" + token);
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
            String result = jobject.get("messageId").getAsString();

            assertNotEquals("Chat room id equals 0 ", 0, result);
        } catch (IOException e) {
            fail("IOException was appeared");
        }
    }

    @Test
    public void testFindAllMessages() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserRequestDto userRequestDto = new UserRequestDto("petya5", MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userRequestDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, "petya5");
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomRequestDto chatRoomRequestDto = new ChatRoomRequestDto("myRoom");
        json = gson.toJson(chatRoomRequestDto);
        String URL_CREATE_ROOM = URL + "/chat/" + userId + "?token=" + token;
        String roomId = createRoom(json, httpClient, URL_CREATE_ROOM);

        String URL_JOIN_USER_TO_CHAT = URL + "/join/" + roomId + "/" + userId + "?token=" + token;
        joinUserToChat(httpClient, URL_JOIN_USER_TO_CHAT);

        try {
            MessageRequestDto messageRequestDto = new MessageRequestDto("hello chat", Long.parseLong(userId), Long.parseLong(roomId), new Date(System.currentTimeMillis()));
            json = gson.toJson(messageRequestDto);
            HttpPost request = new HttpPost(URL + "/message/" + userId + "?token=" + token);
            request.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(json);
            request.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            String data = EntityUtils.toString(entity);

            JsonElement jelement = new JsonParser().parse(data);
            JsonObject jobject = jelement.getAsJsonObject();
            String result = jobject.get("messageId").getAsString();

            assertNotEquals("Chat room id equals 0 ", 0, result);

            HttpGet requestGet = new HttpGet(URL + "/messages/" + userId + "/" + roomId + "?token=" + token);
            HttpResponse responseGet = httpClient.execute(requestGet);
            int code = responseGet.getStatusLine().getStatusCode();

            assertEquals("Code request for find all messages is incorrect", 200, code);

            entity = responseGet.getEntity();

            data = EntityUtils.toString(entity);

            jelement = new JsonParser().parse(data);
            JsonArray jsonArray = jelement.getAsJsonArray();
            assertNotEquals("Messages were not found", 0, jsonArray.size());
        } catch (IOException e) {
            fail("IOException was appeared");
        }

    }
}
