package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.RoleService;
import com.simasforum.SimasForum.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Test
    @DisplayName("RegisterTest")
    void registerTest_isOk() throws Exception {
        User userMock = new User("name", "name@gmail.com", "password", new Role("user"));
        when(userService.getUserByEmail("name@gmail.com")).thenReturn(new User("", "", "", new Role("")));
        when(roleService.getRoleById(anyLong())).thenReturn(Optional.of(new Role("user")));
        when(userService.addUser(userMock)).thenReturn(userMock);
        mockMvc.perform(post("/user/register")
                .param("name", "name")
                .param("email", "name@gmail.com")
                .param("password", "password")
                .param("conf_password", "password")
        ).andExpectAll(status().is3xxRedirection());
    }

    @Test
    @DisplayName("RegisterTest")
    void registerTest_emailExists_isOk() throws Exception {
        User userMock = new User("name", "name@gmail.com", "password", new Role("user"));
        when(userService.getUserByEmail("name@gmail.com")).thenReturn(userMock);
        when(roleService.getRoleById(anyLong())).thenReturn(Optional.of(new Role("user")));
        mockMvc.perform(post("/user/register")
                .param("name", "name")
                .param("email", "name@gmail.com")
                .param("password", "password")
                .param("conf_password", "password")
        ).andExpectAll(status().isOk());
    }

    @Test
    @DisplayName("HTTP GET '/user/register' show register.html ")
    void showRegisterPage_html() throws Exception {
        mockMvc.perform(get("/user/register")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("</html>")),
                content().string(containsString("<form")),
                content().string(containsString("<input")),
                content().string(containsString("<input")),
                content().string(containsString("<input")),
                content().string(containsString("<input"))
        );
    }

    @Test
    @DisplayName("LoginTest")
    void LoginTest_IsOk() throws Exception {
        User userMock = new User("name", "name@gmail.com", "password", new Role("user"));
        when(userService.getUserByEmail(userMock.getEmail())).thenReturn(userMock);
        mockMvc.perform(post("/user/login").param("email", "name@gmail.com").param("password", "password"))
                .andExpectAll(status().is3xxRedirection()
                );
    }

    @Test
    @DisplayName("LoginTest")
    void LoginTest_userNotExists_IsOk() throws Exception {
        User userMock = new User("name", "name@gmail.com", "password", new Role("user"));
        when(userService.getUserByEmail(userMock.getEmail())).thenReturn(null);
        mockMvc.perform(post("/user/login").param("email", "name@gmail.com").param("password", "password"))
                .andExpectAll(status().is3xxRedirection()
                );
    }

    @Test
    @DisplayName("LoginTest")
    void LoginTest_wrongPassword_IsOk() throws Exception {
        User userMock = new User("name", "name@gmail.com", "password", new Role("user"));
        when(userService.getUserByEmail(userMock.getEmail())).thenReturn(userMock);
        mockMvc.perform(post("/user/login").param("email", "name@gmail.com").param("password", "salah"))
                .andExpectAll(status().is3xxRedirection()
                );
    }

    @Test
    @DisplayName("HTTP GET '/user/login' show login.html ")
    void showLoginPage_html() throws Exception {
        mockMvc.perform(get("/user/login")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("</html>")),
                content().string(containsString("<form")),
                content().string(containsString("<input")),
                content().string(containsString("<input"))
        );
    }

    @Test
    @DisplayName("Logout Test")
    void logout_ok() throws Exception {
        mockMvc.perform(get("/user/logout"))
                .andExpectAll(status().is3xxRedirection()
                );
    }
}
