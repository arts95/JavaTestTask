package com.practice.demo.service;

import com.practice.demo.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    UserRole create(UserRole role);

    UserRole read(Long id);

    UserRole readByUserId(Long userID);

    UserRole update(UserRole role);

    void delete(UserRole role);

    UserRole delete(Long id);

    List<UserRole> findAll();
}
