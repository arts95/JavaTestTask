package com.practice.demo.service;

import com.practice.demo.dto.request.CreateUserRequestDTO;
import com.practice.demo.dto.request.UpdateUserRequestDTO;
import com.practice.demo.entity.UserEntity;
import com.practice.demo.exception.EntityNotFoundException;
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
    public UserEntity create(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity create(CreateUserRequestDTO userRequestDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(userRequestDTO.getEmail());
        user.setName(userRequestDTO.getName());
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity read(Long id) {
        return userRepository.findOne(id);
    }

    @Transactional
    @Override
    public UserEntity update(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity update(UpdateUserRequestDTO userRequestDTO, Long id) {
        UserEntity user = this.read(id);

        if (user == null) {
            throw new EntityNotFoundException("User with %s not exists", id.toString());
        }

        user.setName(userRequestDTO.getName());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(UserEntity user) {
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public UserEntity delete(Long id) {
        UserEntity user = userRepository.findOne(id);
        delete(user);
        return user;
    }

    @Transactional
    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}