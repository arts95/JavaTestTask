package com.practice.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRoleListItem {
    @JsonProperty("id")
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
