package com.practice.demo.service;


import com.practice.demo.entity.User;

import java.util.List;


public interface UserService {
    User create(User user);

    User read(Long id);

    User readByEmail(String email);

    User update(User user);

    void delete(User user);

    User delete(Long id);

    Long getIdByEmail(String email);

    List<User> findAll();
}
