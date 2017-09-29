package com.practice.demo.controller;

import com.practice.demo.dto.request.CreateUserRequest;
import com.practice.demo.dto.request.UpdateUserRequest;
import com.practice.demo.dto.response.UserDetail;
import com.practice.demo.dto.response.UserListItem;
import com.practice.demo.entity.User;
import com.practice.demo.exception.EntityNotFoundException;
import com.practice.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetail create(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setName(createUserRequest.getName());
        userService.create(user);
        UserDetail userDetail = new UserDetail();
        userDetail.setEmail(user.getEmail());
        userDetail.setName(user.getName());
        return userDetail;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetail update(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        User user = userService.read(id);
        if (user == null) {
            throw new EntityNotFoundException("User with %s not exists", id.toString());
        }
        user.setName(updateUserRequest.getName());
        user = userService.update(user);
        UserDetail userDetail = new UserDetail();
        userDetail.setEmail(user.getEmail());
        userDetail.setName(user.getName());
        return userDetail;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    public UserDetail get(@PathVariable("id") Long id) {
        User user = userService.read(id);
        if (user == null) {
            throw new EntityNotFoundException("User with %s not exists", id.toString());
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setEmail(user.getEmail());
        userDetail.setName(user.getName());
        return userDetail;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id) {
        User user = userService.read(id);
        if (user == null) {
            throw new EntityNotFoundException("User with %s not exists", id.toString());
        }
        Long userId = user.getId();
        userService.delete(user);
        return userId;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public List<UserListItem> getList() {
        return userService.findAll().stream()
                .map(user -> {
                    UserListItem userListItem = new UserListItem();
                    userListItem.setId(user.getId());
                    userListItem.setEmail(user.getEmail());
                    userListItem.setName(user.getName());
                    return userListItem;
                }).collect(toList());
    }

}
