package com.practice.demo.service;

import com.practice.demo.dto.request.CreateUserRoleRequestDTO;
import com.practice.demo.dto.request.UpdateUserRequestDTO;
import com.practice.demo.dto.request.UpdateUserRoleRequestDTO;
import com.practice.demo.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleService {
    UserRoleEntity create(UserRoleEntity role);

    UserRoleEntity create(CreateUserRoleRequestDTO userRoleRequestDTO);

    UserRoleEntity read(Long id);

    UserRoleEntity update(UserRoleEntity role);

    UserRoleEntity update(UpdateUserRoleRequestDTO userRoleRequestDTO, Long id);

    void delete(UserRoleEntity role);

    UserRoleEntity delete(Long id);

    List<UserRoleEntity> findAll();
}
