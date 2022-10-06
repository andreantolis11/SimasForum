package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("RegisterTest")
    void registerTest() throws Exception{
        User userMock = new User("name","name@gmail.com","password");
        when(userService.addUser(userMock)).thenReturn(userMock);
        mockMvc.perform(post("/user/register").param("name","name").param("email", "name@gmail.com").param("password","password").param("conf_password","password"))
                .andExpectAll(status().is3xxRedirection()
                );
    }
}