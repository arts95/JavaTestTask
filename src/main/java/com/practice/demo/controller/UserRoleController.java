package com.practice.demo.controller;

import com.practice.demo.dto.request.CreateUserRoleRequest;
import com.practice.demo.dto.request.UpdateUserRequest;
import com.practice.demo.dto.request.UpdateUserRoleRequest;
import com.practice.demo.dto.response.UserDetail;
import com.practice.demo.dto.response.UserRoleDetail;
import com.practice.demo.dto.response.UserRoleListItem;
import com.practice.demo.entity.UserRole;
import com.practice.demo.exception.EntityNotFoundException;
import com.practice.demo.service.UserRoleService;
import com.practice.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "roles")
public class UserRoleController {
    private final UserRoleService userRoleService;
    private final UserService userService;

    public UserRoleController(UserRoleService userRoleService, UserService userService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoleDetail create(@Valid @RequestBody CreateUserRoleRequest createUserRoleRequest) {
        UserRole userRole = new UserRole();
        userRole.setName(createUserRoleRequest.getName());
        userRole.setUser(userService.read(createUserRoleRequest.getUserID()));

        userRoleService.create(userRole);
        UserRoleDetail userRoleDetail = new UserRoleDetail();
        userRoleDetail.setName(userRole.getName());
        UserDetail userDetail = new UserDetail();
        userDetail.setEmail(userRole.getUser().getEmail());
        userDetail.setName(userRole.getUser().getName());
        userRoleDetail.setUser(userDetail);
        return userRoleDetail;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserRoleDetail update(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserRoleRequest updateUserRoleRequest) {
        UserRole userRole = userRoleService.read(id);

        if (userRole == null) {
            throw new EntityNotFoundException("User role with %s not exists", id.toString());
        }

        userRole.setName(updateUserRoleRequest.getName());
        userRoleService.update(userRole);

        UserDetail userDetail = new UserDetail();
        userDetail.setEmail(userRole.getUser().getEmail());
        userDetail.setName(userRole.getUser().getName());

        UserRoleDetail userRoleDetail = new UserRoleDetail();
        userRoleDetail.setUser(userDetail);
        userRoleDetail.setName(userRole.getName());

        return userRoleDetail;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    public UserRoleDetail get(@PathVariable("id") Long id) {
        UserRole userRole = userRoleService.read(id);
        if (userRole == null) {
            throw new EntityNotFoundException("User role with %s not exists", id.toString());
        }
        UserRoleDetail userRoleDetail = new UserRoleDetail();
        UserDetail userDetail = new UserDetail();
        userDetail.setEmail(userRole.getUser().getEmail());
        userDetail.setName(userRole.getUser().getName());
        userRoleDetail.setUser(userDetail);
        userRoleDetail.setName(userRole.getName());
        return userRoleDetail;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id) {
        UserRole userRole = userRoleService.read(id);
        if (userRole == null) {
            throw new EntityNotFoundException("User role with %s not exists", id.toString());
        }
        Long userRoleId = userRole.getId();
        userRoleService.delete(userRole);
        return userRoleId;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public List<UserRoleListItem> getList() {
        return userRoleService.findAll().stream()
                .map(userRole -> {
                    UserRoleListItem userRoleListItem = new UserRoleListItem();
                    UserDetail userDetail = new UserDetail();
                    userDetail.setName(userRole.getUser().getName());
                    userDetail.setEmail(userRole.getUser().getEmail());
                    userRoleListItem.setUser(userDetail);
                    userRoleListItem.setName(userRole.getName());
                    userRoleListItem.setId(userRole.getId());
                    return userRoleListItem;
                }).collect(toList());
    }

}
