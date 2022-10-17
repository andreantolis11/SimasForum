package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import com.simasforum.SimasForum.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ThreadService threadService;
    @MockBean
    private UserService userService;
    @MockBean
    private ReplyService replyService;
    @MockBean
    private VoteService voteService;

    @Test
    void threadUpVote_ifNotLogin_ok() throws Exception {
        mockMvc.perform(post("/thread/1/true")).andExpectAll(
                redirectedUrl("/user/login")
        );
    }

    @Test
    void threadUpVote_ifLogin_ok() throws Exception {
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        User fadhlul = new User("fadhlul", "fad@gmail.com", "123", new Role("user"));
        fadhlul.setId(1L);
        sessionAttr.put("USER_LOGIN_ID", fadhlul.getId());
        when(threadService.getThreadDetail(anyLong())).thenReturn(Optional.of(new Thread()));
        mockMvc.perform(post("/thread/1/true").header("Referer","/").sessionAttrs(sessionAttr)).andExpectAll(
                status().is3xxRedirection()
        );
    }
    @Test
    void replyUpVote_ifNotLogin_ok() throws Exception {
        mockMvc.perform(post("/thread/1/1/true")).andExpectAll(
                redirectedUrl("/user/login")
        );
    }

    @Test
    void replyUpVote_ifLogin_ok() throws Exception {
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        User fadhlul = new User("fadhlul", "fad@gmail.com", "123",new Role());
        fadhlul.setId(1L);
        sessionAttr.put("USER_LOGIN_ID", fadhlul.getId());
        when(replyService.getReplyById(anyLong())).thenReturn(Optional.of(new Reply()));
        mockMvc.perform(post("/thread/1/1/true").header("Referer","/").sessionAttrs(sessionAttr)).andExpectAll(
                status().is3xxRedirection()
        );
    }
}
