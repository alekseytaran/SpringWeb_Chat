package com.teamdev.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private long id;

    private String text;

    private Date creationTime;

    @ManyToOne
    private User user;

    @ManyToOne
    private ChatRoom chatRoom;

    Message() {}

    public Message(String text, Date creationTime, User user, ChatRoom chatRoom) {
        this.text = text;
        this.creationTime = creationTime;
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (!text.equals(message.text)) return false;
        if (!creationTime.equals(message.creationTime)) return false;
        if (!user.equals(message.user)) return false;
        return chatRoom.equals(message.chatRoom);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + creationTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                '}';
    }
}
