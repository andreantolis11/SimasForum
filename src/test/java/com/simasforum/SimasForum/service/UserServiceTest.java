package com.simasforum.SimasForum.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Test
    @DisplayName("Given a new user, addUser should save the user")
    void addUser_ok() {
        User user = new User("testuser","password","email@gmail.com");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.addUser(user);

        assertFalse(user.getName().isEmpty());
    }
}
