package com.practice.demo.service;

import com.practice.demo.entity.UserRole;
import com.practice.demo.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public UserRole create(UserRole role) {
        return userRoleRepository.save(role);
    }

    @Override
    @Transactional
    public UserRole read(Long id) {
        return userRoleRepository.findOne(id);
    }

    @Override
    @Transactional
    public UserRole readByUserId(Long userID) {
        return userRoleRepository.findByUserId(userID);
    }

    @Override
    @Transactional
    public UserRole update(UserRole role) {
        return userRoleRepository.save(role);
    }

    @Override
    @Transactional
    public void delete(UserRole user) {
        userRoleRepository.delete(user);
    }

    @Override
    @Transactional
    public UserRole delete(Long id) {
        UserRole role = userRoleRepository.findOne(id);
        delete(role);
        return role;
    }

    @Override
    @Transactional
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }
}
