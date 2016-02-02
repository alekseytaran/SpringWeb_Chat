package com.teamdev.requestDto;

public class UserRequestDto {
    private String name;
    private String email;
    private String password;

    public UserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
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

}
