package com.practice.service;

import com.practice.demo.DemoApplication;
import com.practice.demo.entity.User;
import com.practice.demo.repository.UserRepository;
import com.practice.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setup() {

        userRepository.deleteAllInBatch();

        User user = new User();
        user.setEmail("Email");
        user.setName("Arsenal");
        this.user = userRepository.save(user);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User user = new User();
        String name, email;
        name = "Arsenal";
        email = "test@test.com";
        user.setName(name);
        user.setEmail(email);
        user = userService.create(user);

        assertNotNull(user);
        assertTrue(user.getName().equals(name));
        assertTrue(user.getEmail().equals(email));
    }

    @Test
    @Transactional
    public void shouldUpdateUserName() throws Exception {
        String name = "Arsenal+";
        this.user.setName(name);
        User user = userService.update(this.user);

        assertNotNull(user);
        assertTrue(user.getName().equals(name));
        assertTrue(user.getEmail().equals(this.user.getEmail()));
    }

    @Test
    @Transactional
    public void shouldUpdateUserEmail() throws Exception {
        String email = "test2@test.com";
        this.user.setEmail(email);
        User user = userService.update(this.user);

        assertNotNull(user);
        assertTrue(user.getName().equals(this.user.getName()));
        assertTrue(user.getEmail().equals(email));
    }

    @Test
    public void shouldReadUser() throws Exception {
        User user = userService.read(this.user.getId());

        assertNotNull(user);
        assertTrue(user.getName().equals(this.user.getName()));
        assertTrue(user.getEmail().equals(this.user.getEmail()));
    }

    @Test
    public void shouldReadUserByEmail() throws Exception {
        User user = userService.readByEmail(this.user.getEmail());

        assertNotNull(user);
        assertTrue(user.getName().equals(this.user.getName()));
        assertTrue(user.getEmail().equals(this.user.getEmail()));
    }

    @Test
    public void shouldReadUserIdByEmail() throws Exception {
        Long userID = userService.getIdByEmail(this.user.getEmail());

        assertNotNull(userID);
        assertTrue(userID.equals(this.user.getId()));
    }

    @Test
    public void shouldDeleteUserById() throws Exception {
        User user = userService.delete(this.user.getId());

        assertTrue(user.getId().equals(this.user.getId()));
        assertTrue(user.getName().equals(this.user.getName()));
        assertTrue(user.getEmail().equals(this.user.getEmail()));
        assertNull(userService.read(this.user.getId()));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        userService.delete(this.user);
        assertNull(userService.read(this.user.getId()));
    }
}