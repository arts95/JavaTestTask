package com.practice.demo.service;

import com.practice.demo.dto.request.CreateUserRoleRequestDTO;
import com.practice.demo.dto.request.UpdateUserRoleRequestDTO;
import com.practice.demo.entity.UserEntity;
import com.practice.demo.entity.UserRoleEntity;
import com.practice.demo.exception.EntityNotFoundException;
import com.practice.demo.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleRepository userRoleRepository;
    private UserService userService;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserService userService) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserRoleEntity create(UserRoleEntity role) {
        return userRoleRepository.save(role);
    }

    @Override
    public UserRoleEntity create(CreateUserRoleRequestDTO userRoleRequestDTO) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setName(userRoleRequestDTO.getName());
        UserEntity user = userService.read(userRoleRequestDTO.getUserID());
        if (user == null) {
            throw new EntityNotFoundException("User with %s not exists", userRoleRequestDTO.toString());
        }
        userRoleEntity.setUser(user);
        return  userRoleRepository.save(userRoleEntity);
    }

    @Override
    @Transactional
    public UserRoleEntity read(Long id) {
        return userRoleRepository.findOne(id);
    }

    @Override
    @Transactional
    public UserRoleEntity update(UserRoleEntity role) {
        return userRoleRepository.save(role);
    }

    @Override
    public UserRoleEntity update(UpdateUserRoleRequestDTO userRoleRequestDTO, Long id) {
        UserRoleEntity userRoleEntity = this.read(id);
        if (userRoleEntity == null) {
            throw new EntityNotFoundException("UserEntity role with %s not exists", id.toString());
        }

        userRoleEntity.setName(userRoleRequestDTO.getName());
        return userRoleRepository.save(userRoleEntity);
    }

    @Override
    @Transactional
    public void delete(UserRoleEntity user) {
        userRoleRepository.delete(user);
    }

    @Override
    @Transactional
    public UserRoleEntity delete(Long id) {
        UserRoleEntity role = userRoleRepository.findOne(id);
        delete(role);
        return role;
    }

    @Override
    @Transactional
    public List<UserRoleEntity> findAll() {
        return userRoleRepository.findAll();
    }
}
