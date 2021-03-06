package com.teamdev.jpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ChatRoom {

    @Id
    @GeneratedValue
    private Long id;

    private String roomName;

    @ManyToMany(mappedBy = "chatRooms", cascade = CascadeType.REFRESH)
    private final Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REFRESH)
    private final List<Message> messages = new ArrayList<>();

    ChatRoom() {}

    public ChatRoom(String roomName) {
        this.roomName = roomName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRoom chatRoom = (ChatRoom) o;

        if (!roomName.equals(chatRoom.roomName)) return false;
        if (users != null ? !users.equals(chatRoom.users) : chatRoom.users != null) return false;
        return !(messages != null ? !messages.equals(chatRoom.messages) : chatRoom.messages != null);

    }

    @Override
    public int hashCode() {
        return roomName.hashCode();
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "roomName='" + roomName + '\'' +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
}
