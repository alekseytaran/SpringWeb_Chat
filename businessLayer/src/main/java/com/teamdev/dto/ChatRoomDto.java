package com.teamdev.dto;

public class ChatRoomDto {
    private long id;
    private String roomName;

    public ChatRoomDto(long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
