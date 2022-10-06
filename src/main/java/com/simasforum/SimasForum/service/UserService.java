package com.simasforum.SimasForum.service;


import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.UserRepository;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
    public User getUserById(Long id) throws NoSuchElementException {
        Optional<User> result = userRepository.findById(id);
        return result.get();
    }
    public User getUserByEmail(String email) throws NoSuchElementException {
        Optional<User> result = userRepository.findByEmail(email);
        return result.get();
    }
    
}