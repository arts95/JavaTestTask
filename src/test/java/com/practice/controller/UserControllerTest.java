package com.practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.demo.DemoApplication;
import com.practice.demo.controller.CommonExceptionHandlerController;
import com.practice.demo.controller.UserController;
import com.practice.demo.dto.request.CreateUserRequest;
import com.practice.demo.dto.request.UpdateUserRequest;
import com.practice.demo.entity.User;
import com.practice.demo.repository.UserRepository;

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


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new CommonExceptionHandlerController()).build();

        userRepository.deleteAllInBatch();

        User user = new User();
        user.setEmail("Email");
        user.setName("Arsenal");
        this.user = userRepository.save(user);
    }

    @Test
    @Transactional
    public void shouldCreateNewUser() throws Exception {

        CreateUserRequest user = new CreateUserRequest();
        user.setEmail("test@test.com");
        user.setName("Name Name");

        String json = MAPPER.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
                .andExpect(status().is2xxSuccessful());

        User actualUser = userRepository.findByEmail(user.getEmail());
        assertThat(actualUser, notNullValue());
        assertThat(actualUser.getEmail(), equalTo(user.getEmail()));
        assertThat(actualUser.getName(), equalTo(user.getName()));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        String name = "Updated";
        UpdateUserRequest user = new UpdateUserRequest();

        user.setName(name);

        String json = MAPPER.writeValueAsString(user);

        mockMvc.perform(put("/users/" + this.user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(this.user.getEmail()))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldReturnUser() throws Exception {
        mockMvc.perform(get("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void shouldNotReturnUser() throws Exception {
            mockMvc.perform(get("/users/0")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnUserList() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}