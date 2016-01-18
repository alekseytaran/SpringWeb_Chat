package com.teamdev.dto;

public class ChatRoomDto {
    private Long id;
    private String roomName;

    public ChatRoomDto(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
