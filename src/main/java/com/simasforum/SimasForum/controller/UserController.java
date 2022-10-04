package com.simasforum.SimasForum.controller;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.UserService;

@Controller
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public void setTodoListService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/register")
    public String registerUser(){
    	return "register";
    }
    
    @PostMapping("/user/register")
    public String submitUser(@RequestParam("name") String name,
    					  @RequestParam("email") String email,
    					  @RequestParam("password") String password,
    					  @RequestParam("conf_password") String conf_password) {
        User loginUser = userService.addUser(new User(name, email, password));
        System.out.println(loginUser.getName()+" is registered");
        return "redirect:/user/login";
    }
    
    @GetMapping("/user/login")
    public String loginForm(){
    	return "login";
    }
    
    @PostMapping("/user/login")
    public String loginUser(@RequestParam("email") String email,
    					  @RequestParam("password") String password) {
        User loginUser = userService.getUserByEmail(email);
        if(!loginUser.getPassword().equals(password)) {
        	//TODO: wrong password handler
        	return null;
        }
        //TODO: login entah ini gmna caranya
        return "redirect:/user/login";
    }

    @ExceptionHandler
    public String handleException(NoSuchElementException exception) {
        return "404";
    }
}
