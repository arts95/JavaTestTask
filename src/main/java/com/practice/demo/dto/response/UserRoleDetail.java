package com.practice.demo.dto.response;


public class UserRoleDetail {
    private String name;
    private UserDetail user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDetail getUser() {
        return user;
    }

    public void setUser(UserDetail user) {
        this.user = user;
    }
}
