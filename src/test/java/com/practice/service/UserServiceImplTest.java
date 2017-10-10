package com.practice.service;

import com.practice.demo.DemoApplication;
import com.practice.demo.entity.UserEntity;
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
@SpringBootTest(classes = DemoApplication.class) //TODO
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository.deleteAllInBatch();
    }

    @Test
    public void shouldCreateUser() throws Exception {
        UserEntity user = new UserEntity();
        String name = "Arsenal";
        String email = "test@test.com";
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
        UserEntity newUser = this.getNewUser();
        newUser.setName(name);

        UserEntity user = userService.update(newUser);

        assertNotNull(user);
        assertTrue(user.getName().equals(name));
        assertTrue(user.getEmail().equals(newUser.getEmail()));
    }

    @Test
    @Transactional
    public void shouldUpdateUserEmail() throws Exception {
        String email = "test2@test.com";

        UserEntity newUser = this.getNewUser();
        newUser.setEmail(email);

        UserEntity user = userService.update(newUser);

        assertNotNull(user);
        assertTrue(user.getName().equals(newUser.getName()));
        assertTrue(user.getEmail().equals(email));
    }

    @Test
    public void shouldReadUser() throws Exception {
        UserEntity newUser = this.getNewUser();
        UserEntity user = userService.read(newUser.getId());

        assertNotNull(user);
        assertTrue(user.getName().equals(newUser.getName()));
        assertTrue(user.getEmail().equals(newUser.getEmail()));
    }

    @Test
    public void shouldDeleteUserById() throws Exception {
        UserEntity newUser = this.getNewUser();
        UserEntity user = userService.delete(newUser.getId());

        assertTrue(user.getId().equals(newUser.getId()));
        assertTrue(user.getName().equals(newUser.getName()));
        assertTrue(user.getEmail().equals(newUser.getEmail()));
        assertNull(userService.read(newUser.getId()));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        UserEntity newUser = this.getNewUser();
        userService.delete(newUser);
        assertNull(userService.read(newUser.getId()));
    }

    private UserEntity getNewUser() {
        UserEntity user = new UserEntity();
        user.setEmail("Email");
        user.setName("Arsenal");
        return userRepository.save(user);
    }

}