package com.practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.demo.DemoApplication;
import com.practice.demo.controller.CommonExceptionHandlerController;
import com.practice.demo.controller.UserRoleController;
import com.practice.demo.dto.request.CreateUserRoleRequest;
import com.practice.demo.dto.request.UpdateUserRoleRequest;
import com.practice.demo.entity.User;
import com.practice.demo.entity.UserRole;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
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

    private UserRole userRole;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRoleController).setControllerAdvice(new CommonExceptionHandlerController()).build();

        userRoleRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        User user = new User();
        user.setEmail("Email2");
        user.setName("Arsenal");

        UserRole userRole = new UserRole();
        userRole.setName("Name");
        userRole.setUser(userRepository.save(user));
        this.userRole = userRoleRepository.save(userRole);
    }

    @Test
    @Transactional
    public void shouldCreateNewUserRole() throws Exception {
        CreateUserRoleRequest userRole = new CreateUserRoleRequest();

        User user = new User();
        user.setEmail("Email3");
        user.setName("Arsenal");
        User actualUser = userRepository.save(user);

        userRole.setUserID(actualUser.getId());
        userRole.setName("Name Name");

        String json = MAPPER.writeValueAsString(userRole);

        mockMvc.perform(post("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is2xxSuccessful());

        UserRole actualRole = userRoleRepository.findByUserId(actualUser.getId());
        assertThat(actualRole, notNullValue());
        assertThat(actualRole.getUser().getId(), equalTo(userRole.getUserID()));
        assertThat(actualRole.getName(), equalTo(userRole.getName()));
    }

    @Test
    @Transactional
    public void shouldUpdateUserRole() throws Exception {
        String name = "Updated";
        UpdateUserRoleRequest userRoleRequest = new UpdateUserRoleRequest();

        userRoleRequest.setName(name);

        String json = MAPPER.writeValueAsString(userRoleRequest);

        mockMvc.perform(put("/roles/" + this.userRole.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotReturnUser() throws Exception {
        mockMvc.perform(get("/roles/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void shouldReturnUserRole() throws Exception {
        mockMvc.perform(get("/roles/" + userRole.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userRole.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.name").value(userRole.getUser().getName()))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @Transactional
    public void shouldReturnUserRoleList() throws Exception {
        mockMvc.perform(get("/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    public void shouldDeleteUserRole() throws Exception {
        mockMvc.perform(delete("/roles/" + userRole.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
