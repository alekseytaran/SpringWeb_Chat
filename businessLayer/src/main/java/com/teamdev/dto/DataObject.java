package com.teamdev.dto;

import com.google.common.collect.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DataObject {

    private String userName;
    private long countRooms;
    private Set<String> chatRoomsByIdUser = new HashSet<>();

    public DataObject() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(long countRooms) {
        this.countRooms = countRooms;
    }

    public void setChatRoomsByIdUser(ImmutableSet<ChatRoomDto> chatRoomsByIdUser) {
        Set<String> roomsName = new HashSet<>();

        for (ChatRoomDto chatRoomDto: chatRoomsByIdUser) {
            roomsName.add(chatRoomDto.getRoomName());
        }
        this.chatRoomsByIdUser = roomsName;
    }

    @Override
    public String toString() {
        return "DataObject [" +
                "chatRoomsByIdUser=" + chatRoomsByIdUser +
                ", userName='" + userName + '\'' +
                ", countRooms=" + countRooms +
                ']';
    }
}
