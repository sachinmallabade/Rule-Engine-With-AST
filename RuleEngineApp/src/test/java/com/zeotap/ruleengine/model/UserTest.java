package com.zeotap.ruleengine.model;

import com.zeotap.ruleengine.Model.User;
import com.zeotap.ruleengine.Repository.UserRepository;
import com.zeotap.ruleengine.Service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setAge(30);
        user.setDepartment("Sales");
        user.setSalary(60000);
        user.setExperience(5);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("Sales", createdUser.getDepartment());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);
        assertNotNull(foundUser);
        assertEquals(user.getDepartment(), foundUser.getDepartment());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        List<User> foundUsers = userService.getAllUsers();
        assertEquals(1, foundUsers.size());
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        User updatedUser = new User();
        updatedUser.setDepartment("Marketing");
        userService.updateUser(1L, updatedUser);
        
        assertEquals("Marketing", user.getDepartment());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
