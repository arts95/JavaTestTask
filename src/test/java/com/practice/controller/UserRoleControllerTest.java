package com.practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.demo.DemoApplication;
import com.practice.demo.controller.CommonExceptionHandlerController;
import com.practice.demo.controller.UserRoleController;
import com.practice.demo.dto.request.CreateUserRoleRequestDTO;
import com.practice.demo.dto.request.UpdateUserRoleRequestDTO;
import com.practice.demo.entity.UserEntity;
import com.practice.demo.entity.UserRoleEntity;
import com.practice.demo.repository.UserRepository;
import com.practice.demo.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserRoleControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private UserRoleController userRoleController;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRoleController).setControllerAdvice(new CommonExceptionHandlerController()).build();

        userRoleRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @Transactional
    public void shouldCreateNewUserRole() throws Exception {
        CreateUserRoleRequestDTO userRole = new CreateUserRoleRequestDTO();
        UserEntity user = this.getNewUser();
        userRole.setUserID(user.getId());
        userRole.setName("Name1");

        String json = MAPPER.writeValueAsString(userRole);

        mockMvc.perform(post("/users/" + user.getId() + "/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userRole.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.name").value(user.getName()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void shouldUpdateUserRole() throws Exception {
        String name = "Updated";
        UpdateUserRoleRequestDTO userRoleRequest = new UpdateUserRoleRequestDTO();

        userRoleRequest.setName(name);

        String json = MAPPER.writeValueAsString(userRoleRequest);
        UserRoleEntity userRole = this.getNewUserRole();
        mockMvc.perform(put("/users/" + userRole.getUser().getId() + "/roles/" + userRole.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotReturnUser() throws Exception {
        mockMvc.perform(get("/users/0/roles/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void shouldReturnUserRole() throws Exception {
        UserRoleEntity userRole = this.getNewUserRole();
        mockMvc.perform(get("/users/" + userRole.getUser().getId() + "/roles/" + userRole.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userRole.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.name").value(userRole.getUser().getName()))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @Transactional
    public void shouldReturnUserRoleList() throws Exception {
        UserRoleEntity userRole = this.getNewUserRole();
        mockMvc.perform(get("/users/" + userRole.getUser().getId() + "/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    public void shouldDeleteUserRole() throws Exception {
        UserRoleEntity userRole = this.getNewUserRole();
        mockMvc.perform(delete("/users/" + userRole.getUser().getId() + "/roles/" + userRole.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private UserRoleEntity getNewUserRole() {
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setName("Name");
        userRole.setUser(this.getNewUser());
        return userRoleRepository.save(userRole);
    }

    private UserEntity getNewUser() {
        UserEntity user = new UserEntity("Arsenal" + Math.random(), "Email@email.com" + Math.random());
        return userRepository.save(user);
    }

}
