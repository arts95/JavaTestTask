package com.practice.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.demo.entity.UserRoleEntity;

public class UserRoleListItemDTO {
    @JsonProperty("id")
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoleListItemDTO(UserRoleEntity userRoleEntity) {
        this.id = userRoleEntity.getId();
        this.name = userRoleEntity.getName();
        this.user = new UserDetailDTO(userRoleEntity.getUser());
    }
}
