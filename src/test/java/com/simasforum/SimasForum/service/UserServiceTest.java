package com.simasforum.SimasForum.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
        User registeredUser = userService.addUser(user);
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    @DisplayName("Given a new user, addUser should save the user")
    void getUserById_ok() {
        Long id = 1L;
        Optional<User> mockUser= Optional.of(new User("name","email@email.com","password"));
        when(userRepository.findById(anyLong())).thenReturn(mockUser);
        User registeredUser = userService.getUserById(id);
        verify(userRepository, times(1)).findById(any(Long.class));
        assertTrue(registeredUser.getName().equals("name"));
    }
    
    @Test
    @DisplayName("Given a new user, addUser should save the user")
    void getUserEmail_ok() {
    	String email = "email@email.com";
    	Optional<User> mockUser= Optional.of(new User("name",email,"password"));
    	when(userRepository.findByEmail(anyString())).thenReturn(mockUser);
    	User registeredUser = userService.getUserByEmail(email);
    	verify(userRepository, times(1)).findByEmail(any(String.class));
    	assertTrue(registeredUser.getEmail().equals(email));
    }
    
    
}
