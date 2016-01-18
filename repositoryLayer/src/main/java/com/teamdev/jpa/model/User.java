package com.teamdev.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable{

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String mail;

    private String password;

    @ManyToMany
    private final Set<ChatRoom> chatRooms = new HashSet<>();

    @OneToMany
    private final Set<Message> messages = new HashSet<>();

    public User() {}

    public User(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public Set<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (!mail.equals(user.mail)) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (chatRooms != null ? !chatRooms.equals(user.chatRooms) : user.chatRooms != null) return false;
        return !(messages != null ? !messages.equals(user.messages) : user.messages != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + mail.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
