package com.teamdev.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class AuthenticationToken {

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
