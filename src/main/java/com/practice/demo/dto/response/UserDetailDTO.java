package com.practice.demo.dto.response;

import com.practice.demo.entity.UserEntity;

public class UserDetailDTO {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDetailDTO(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
    }
}
