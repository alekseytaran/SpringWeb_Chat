package com.teamdev.requestDto;

public class ChatRoomRequestDto {
    private Long id;
    private String roomName;

    public ChatRoomRequestDto(Long id, String roomName) {
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
