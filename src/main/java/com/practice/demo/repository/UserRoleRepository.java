package com.practice.demo.repository;


import com.practice.demo.entity.User;
import com.practice.demo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByUserId(Long userID);
}
