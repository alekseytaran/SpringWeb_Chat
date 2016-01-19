package com.teamdev.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.REMOVE)
    private AuthenticationToken authenticationToken;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private final Set<ChatRoom> chatRooms = new HashSet<>();

    @OneToMany(cascade = CascadeType.REFRESH)
    private final Set<Message> messages = new HashSet<>();

    User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
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
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (!email.equals(user.email)) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (chatRooms != null ? !chatRooms.equals(user.chatRooms) : user.chatRooms != null) return false;
        return !(messages != null ? !messages.equals(user.messages) : user.messages != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
