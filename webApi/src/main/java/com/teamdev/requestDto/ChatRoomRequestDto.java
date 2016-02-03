package com.teamdev.requestDto;

public class ChatRoomRequestDto {
    private String roomName;

    public ChatRoomRequestDto(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

}
