package com.practice.demo.dto.response;


import com.practice.demo.entity.UserRoleEntity;

public class UserRoleDetailDTO {
    private String name;
    private UserDetailDTO user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDetailDTO getUser() {
        return user;
    }

    public void setUser(UserDetailDTO user) {
        this.user = user;
    }

    public UserRoleDetailDTO(UserRoleEntity userRoleEntity) {
        this.name = userRoleEntity.getName();
        this.user = new UserDetailDTO(userRoleEntity.getUser());
    }
}
