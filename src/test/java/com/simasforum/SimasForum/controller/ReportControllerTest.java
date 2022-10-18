package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReportService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @MockBean
    private UserService userService;

    @MockBean
    private ThreadService threadService;

    @Test
    void redirectAfterReport_ok() throws Exception {
        LocalDate date = LocalDate.of(2020, 1, 8);
        Role user = new Role("user");
        user.setId(1L);
        User john = new User("john", "john@gmail.com", "john12345", user);
        john.setId(1L);
        Thread thread = new Thread(john, "Title Spam", "Content Spam", 0, date);
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("USER_LOGIN_ID", 1L);

        when(threadService.getThreadDetail(anyLong())).thenReturn(Optional.of(thread));
        when(userService.getUserById(anyLong())).thenReturn(john);

        mockMvc.perform(post("/thread/report/1/1").param("alasan", "spam").sessionAttrs(sessionAttr)).andExpectAll(
                status().is3xxRedirection()
        );
    }
}