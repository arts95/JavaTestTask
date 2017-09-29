package com.practice.demo.service;

import com.practice.demo.entity.User;
import com.practice.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User read(Long id) {
        return userRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User readByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getIdByEmail(String email) {
        return userRepository.findByEmail(email).getId();
    }

    @Transactional
    @Override
    public User delete(Long id) {
        User user = userRepository.findOne(id);
        delete(user);
        return user;
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}