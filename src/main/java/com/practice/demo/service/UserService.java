package com.practice.demo.service;


import com.practice.demo.dto.request.CreateUserRequestDTO;
import com.practice.demo.dto.request.UpdateUserRequestDTO;
import com.practice.demo.entity.UserEntity;

import java.util.List;


public interface UserService {
    UserEntity create(UserEntity user);

    UserEntity create(CreateUserRequestDTO userRequestDTO);

    UserEntity read(Long id);

    UserEntity update(UserEntity user);

    UserEntity update(UpdateUserRequestDTO userRequestDTO, Long id);

    void delete(UserEntity user);

    UserEntity delete(Long id);

    List<UserEntity> findAll();
}
