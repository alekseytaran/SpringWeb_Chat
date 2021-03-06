package com.teamdev.dto;

public class UserDto implements Comparable<UserDto> {
    private Long id;
    private String name;
    private String email;
    private String password;

    public UserDto(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserDto(String name, String email, String password) {
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

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(UserDto anotherUserDto) {
        return this.id > anotherUserDto.id ? 1 : -1 ;
    }
}
