package com.practice.demo.controller;

import com.practice.demo.dto.request.CreateUserRoleRequestDTO;
import com.practice.demo.dto.request.UpdateUserRoleRequestDTO;
import com.practice.demo.dto.response.UserRoleDetailDTO;
import com.practice.demo.dto.response.UserRoleListItemDTO;
import com.practice.demo.entity.UserRoleEntity;
import com.practice.demo.exception.EntityNotFoundException;
import com.practice.demo.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "users/{id}/roles")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoleDetailDTO create(@Valid @RequestBody CreateUserRoleRequestDTO createUserRoleRequest) {
        return new UserRoleDetailDTO(userRoleService.create(createUserRoleRequest));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserRoleDetailDTO update(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserRoleRequestDTO updateUserRoleRequest) {
        return new UserRoleDetailDTO(userRoleService.update(updateUserRoleRequest, id));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public UserRoleDetailDTO get(@PathVariable("id") Long id) {
        UserRoleEntity userRole = userRoleService.read(id);
        if (userRole == null) {
            throw new EntityNotFoundException("User role with %s not exists", id.toString());
        }
        return new UserRoleDetailDTO(userRole);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long delete(@PathVariable("id") Long id) {
        UserRoleEntity userRole = userRoleService.read(id);
        if (userRole == null) {
            throw new EntityNotFoundException("User role with %s not exists", id.toString());
        }
        Long userRoleId = userRole.getId();
        userRoleService.delete(userRole);
        return userRoleId;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public List<UserRoleListItemDTO> getList() {
        return userRoleService.findAll().stream()
                .map(userRole -> {
                    return new UserRoleListItemDTO(userRole);
                }).collect(toList());
    }

}
