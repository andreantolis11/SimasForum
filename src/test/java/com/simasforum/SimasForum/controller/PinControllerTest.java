package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.PinService;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ThreadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PinController.class)
@AutoConfigureMockMvc(addFilters = false)
class PinControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReplyService replyService;
    @MockBean
    private PinService pinService;
    @MockBean
    private ThreadService threadService;

    @Test
    void pinReply_ok() throws Exception{
        HashMap<String, Object> sessionAttr = new HashMap<String,Object>();
        User test = new User("test","test@gmail.com","test",new Role("user"));
        test.setId(1L);
        sessionAttr.put("USER_LOGIN_ID", test.getId());
        when(replyService.getReplyById(anyLong())).thenReturn(Optional.of(new Reply()));
        mockMvc.perform(post("/pin/reply/1/1").header("Referer","/").sessionAttrs(sessionAttr)).andExpectAll(
                status().isOk()
        );
    }

    @Test
    void pinThread_ok() throws Exception{
        HashMap<String, Object> sessionAttr = new HashMap<String,Object>();
        User test = new User("test","test@gmail.com","test",new Role("user"));
        test.setId(1L);
        sessionAttr.put("USER_LOGIN_ID", test.getId());
        when(threadService.getThreadDetail(anyLong())).thenReturn(Optional.of(new Thread()));
        mockMvc.perform(get("/pin/thread/1").sessionAttrs(sessionAttr)).andExpectAll(
                status().is3xxRedirection()
        );
    }
}
