package com.practice.demo.controller;

import com.practice.demo.dto.request.CreateUserRequestDTO;
import com.practice.demo.dto.request.UpdateUserRequestDTO;
import com.practice.demo.dto.response.UserDetailDTO;
import com.practice.demo.dto.response.UserListItemDTO;
import com.practice.demo.entity.UserEntity;
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
    public UserDetailDTO create(@Valid @RequestBody CreateUserRequestDTO createUserRequest) {
        return new UserDetailDTO(userService.create(createUserRequest));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailDTO update(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserRequestDTO updateUserRequest) {
        return new UserDetailDTO(userService.update(updateUserRequest, id));
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    public UserDetailDTO get(@PathVariable("id") Long id) {
        UserEntity user = userService.read(id);
        if (user == null) {
            throw new EntityNotFoundException("User with %s not exists", id.toString());
        }
        return new UserDetailDTO(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id) {
        UserEntity user = userService.read(id);
        if (user == null) {
            throw new EntityNotFoundException("User with %s not exists", id.toString());
        }
        Long userId = user.getId();
        userService.delete(user);
        return userId;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public List<UserListItemDTO> getList() {
        return userService.findAll().stream()
                .map(user -> {
                    UserListItemDTO userListItem = new UserListItemDTO();
                    userListItem.setId(user.getId());
                    userListItem.setEmail(user.getEmail());
                    userListItem.setName(user.getName());
                    return userListItem;
                }).collect(toList());
    }

}
