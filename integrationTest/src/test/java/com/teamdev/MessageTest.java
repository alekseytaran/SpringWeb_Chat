package com.teamdev;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamdev.requestDto.ChatRoomDto;
import com.teamdev.requestDto.LogInDto;
import com.teamdev.requestDto.MessageRequestDto;
import com.teamdev.requestDto.UserDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.UserId;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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

    @Test
    public void testSendMessage() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        UserDto userDto = new UserDto(0L, NAME, MAIL, PASSWORD);
        Gson gson = new Gson();
        String json = gson.toJson(userDto);
        String userId = signUp(json, httpClient, URL_SIGN_UP);

        LogInDto logInDto = new LogInDto(PASSWORD, NAME);
        json = gson.toJson(logInDto);
        String token = logIn(json, httpClient, URL_LOG_IN);

        ChatRoomDto chatRoomDto = new ChatRoomDto(0L, "myRoom");
        json = gson.toJson(chatRoomDto);
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

}
