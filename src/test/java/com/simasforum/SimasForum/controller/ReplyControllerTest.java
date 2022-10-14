package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ThreadService threadService;
    @MockBean
    private UserService userService;
    @MockBean
    private ReplyService replyService;

    @Test
    @DisplayName("reply to thread")
    void replyToThread_ok() throws Exception {
        User user = new User("name", "email", "password");
        Thread mockThread = new Thread(user, "title", "content", 0, null);
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(threadService.getThreadDetail(anyLong())).thenReturn(Optional.of(mockThread));
        mockMvc.perform(post("/reply/thread/1")
                .param("content", "reply content")
                .sessionAttr("USER_LOGIN_ID", 1L)).andExpectAll(
                status().is3xxRedirection()
        );
        verify(replyService, times(1)).addReply(any(Reply.class));
    }

    @Test
    @DisplayName("reply to reply")
    void replyToReply_ok() throws Exception {
        User user = new User("name", "email", "password");
        Thread mockThread = new Thread(user, "title", "content", 0, null);
        Reply reply = new Reply("", "", user, mockThread);
        Reply mockReply = new Reply("", "", user, reply);
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(replyService.getReplyById(anyLong())).thenReturn(Optional.of(mockReply));
        mockMvc.perform(post("/reply/reply/1")
                .param("content", "reply content")
                .sessionAttr("USER_LOGIN_ID", 1L)).andExpectAll(
                status().is3xxRedirection()
        );
        verify(replyService, times(1)).addReply(any());
    }

    @Test
    @DisplayName("reply to thread not login")
    void replyToThread_IfNotLogin() throws Exception {
        mockMvc.perform(post("/reply/thread/1")
                .param("content", "reply content")).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/user/login")
        );
    }
    @Test
    @DisplayName("reply to reply not login")
    void replyToReply_IfNotLogin() throws Exception {
        mockMvc.perform(post("/reply/reply/1")
                .param("content", "reply content")).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/user/login")
        );
    }
}
