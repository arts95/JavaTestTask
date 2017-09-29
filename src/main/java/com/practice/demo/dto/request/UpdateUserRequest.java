package com.practice.demo.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class UpdateUserRequest {
    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}