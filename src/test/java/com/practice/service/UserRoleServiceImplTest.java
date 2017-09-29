package com.practice.service;

import com.practice.demo.DemoApplication;
import com.practice.demo.entity.User;
import com.practice.demo.entity.UserRole;
import com.practice.demo.repository.UserRepository;
import com.practice.demo.repository.UserRoleRepository;
import com.practice.demo.service.UserRoleService;
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
public class UserRoleServiceImplTest {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private UserRole userRole;
    private User user;

    @Before
    public void setup() {
        userRoleRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        User user = new User();
        user.setName("Role Arsenal");
        user.setEmail("test@test.com");
        this.user = userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setName("Role Arsenal");
        userRole.setUser(user);
        this.userRole = userRoleRepository.save(userRole);
    }

    @Test
    public void shouldCreateUserRole() throws Exception {
        String name = "Arsenal Role";
        UserRole userRole = new UserRole();
        userRole.setName(name);
        userRole.setUser(this.user);
        userRole = userRoleService.create(userRole);

        assertNotNull(userRole);
        assertNotNull(userRole.getId());
        assertTrue(userRole.getName().equals(name));
        assertTrue(userRole.getUser().equals(this.user));
    }

    @Test
    @Transactional
    public void shouldUpdateUserRoleName() throws Exception {
        String name = "Role+";
        this.userRole.setName(name);
        UserRole userRole = userRoleService.update(this.userRole);

        assertNotNull(userRole);
        assertNotNull(userRole.getId());
        assertTrue(userRole.getName().equals(name));
        assertTrue(userRole.getUser().equals(this.user));
    }

    @Test
    @Transactional
    public void shouldReadUserRole() throws Exception {
        UserRole userRole = userRoleService.read(this.userRole.getId());

        assertNotNull(userRole);
        assertTrue(userRole.getId().equals(this.userRole.getId()));
        assertTrue(userRole.getName().equals(this.userRole.getName()));
        assertTrue(userRole.getUser().getId().equals(this.user.getId()));
    }


    @Test
    @Transactional
    public void shouldReadUserRoleByUserId() throws Exception {
        UserRole userRole = userRoleService.readByUserId(this.user.getId());

        assertNotNull(userRole);
        assertTrue(userRole.getId().equals(this.userRole.getId()));
        assertTrue(userRole.getName().equals(this.userRole.getName()));
        assertTrue(userRole.getUser().getId().equals(this.user.getId()));
    }

    @Test
    @Transactional
    public void shouldDeleteUserRoleById() throws Exception {
        UserRole userRole = userRoleService.delete(this.userRole.getId());

        assertTrue(userRole.getId().equals(this.userRole.getId()));
        assertTrue(userRole.getName().equals(this.userRole.getName()));
        assertTrue(userRole.getUser().equals(this.user));
        assertNull(userRoleService.read(this.userRole.getId()));
    }

    @Test
    public void shouldDeleteUserRole() throws Exception {
        userRoleService.delete(this.userRole);
        assertNull(userRoleService.read(this.userRole.getId()));
    }
}