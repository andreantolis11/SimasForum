package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.RoleService;
import com.simasforum.SimasForum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Optional;


@Controller
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    private RoleService roleService;

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
    					  @RequestParam("conf_password") String conf_password, Model model,
                             HttpSession session) {

        User userEmail = userService.getUserByEmail(email);

        if(userEmail.getEmail().isBlank()){
            model.addAttribute("EMAIL_EXISTS", false);
            Optional<Role> role = roleService.getRoleById(1L);
            User loginUser = userService.addUser(new User(name, email, password, role.get()));
            System.out.println(loginUser.getName()+" is registered");
            return "redirect:/user/login";
        }else {
//            session.setAttribute("EMAIL_EXISTS", true);
            model.addAttribute("EMAIL_EXISTS", true);
            return "register";
        }
    }
    
    @GetMapping("/user/login")
    public String loginForm(HttpSession session, Model model){
    	model.addAttribute("LOGIN_ERROR", session.getAttribute("LOGIN_ERROR"));
    	return "login";
    }
    
    @PostMapping("/user/login")
    public String loginUser(@RequestParam("email") String email,
    					  @RequestParam("password") String password,
    					  HttpServletRequest request) {
    	User loginUser = null;
        try {
        	loginUser = userService.getUserByEmail(email);
        	if(!loginUser.getPassword().equals(password)) {
        		throw new Exception();
        	}
		} catch (Exception e) {
			request.getSession().setAttribute("LOGIN_ERROR", true);
        	return "redirect:/user/login";
		}
        
        request.getSession().setAttribute("LOGIN_ERROR", false);
        request.getSession().setAttribute("USER_LOGIN_ID", loginUser.getId());
        request.getSession().setAttribute("USER_LOGIN_NAME", loginUser.getName());
        request.getSession().setAttribute("USER_LOGIN_ROLE", loginUser.getRole().getRoleName());
        return "redirect:/dashboard";
    }
    
    @GetMapping("/user/logout")
    public String logoutUser(HttpServletRequest request) {
    	request.getSession().invalidate();
        return "redirect:/dashboard";
    }

    @ExceptionHandler
    public String handleException(NoSuchElementException exception) {
        return "404";
    }
}
