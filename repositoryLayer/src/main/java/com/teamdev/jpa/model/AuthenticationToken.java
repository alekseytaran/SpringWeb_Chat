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
    private long accessTokenId;

    private String accessToken;

    @OneToOne
    private long userId;

    private Date validTime;

    AuthenticationToken() {}

    public AuthenticationToken(String accessToken, long userId, Date validTime) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.validTime = validTime;
    }

    public void setAccessTokenId(long accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationToken that = (AuthenticationToken) o;

        if (userId != that.userId) return false;
        return accessToken != null ? accessToken.equals(that.accessToken) : that.accessToken == null;

    }

    @Override
    public int hashCode() {
        int result = accessToken != null ? accessToken.hashCode() : 0;
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AuthenticationToken{" +
                "accessToken='" + accessToken + '\'' +
                ", userId=" + userId +
                '}';
    }
}
