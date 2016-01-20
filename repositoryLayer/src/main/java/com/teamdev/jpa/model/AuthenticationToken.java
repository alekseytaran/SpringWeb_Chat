package com.teamdev.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class AuthenticationToken implements Serializable{

    @Id
    @GeneratedValue
    private Long accessTokenId;

    private String accessToken;

    @OneToOne
    private User user;

    private Date validTime;

    AuthenticationToken() {}

    public AuthenticationToken(String accessToken, User user, Date validTime) {
        this.accessToken = accessToken;
        this.user = user;
        this.validTime = validTime;
    }

    public Long getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(Long accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationToken that = (AuthenticationToken) o;

        return accessToken != null ? accessToken.equals(that.accessToken) : that.accessToken == null;

    }

    @Override
    public int hashCode() {
        return accessToken != null ? accessToken.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AuthenticationToken{" +
                "user=" + user +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
