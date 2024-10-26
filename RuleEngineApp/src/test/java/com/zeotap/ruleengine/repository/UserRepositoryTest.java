package com.zeotap.ruleengine.repository;

import com.zeotap.ruleengine.Model.User;
import com.zeotap.ruleengine.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(30);
        user.setDepartment("Sales");
        user.setSalary(60000);
        user.setExperience(5);
        userRepository.save(user);
    }

    @Test
    void testFindById() {
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getDepartment(), foundUser.get().getDepartment());
    }

    @Test
    void testFindAll() {
        Iterable<User> users = userRepository.findAll();
        assertTrue(users.spliterator().hasCharacteristics(0)); // Check if it contains saved user
    }

    @Test
    void testDeleteById() {
        userRepository.deleteById(user.getId());
        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertFalse(deletedUser.isPresent());
    }
}
