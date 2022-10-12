package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @MockBean
    private ReplyRepository replyRepository;

    @Test
    void addReplyItem_ok() {
        User user = new User();
        Thread thread = new Thread();
        Reply mockReply = new Reply("reply 1","content 11",user,thread);
        replyService.addReply(mockReply);
        verify(replyRepository, times(1)).save(ArgumentMatchers.any(Reply.class));
    }

    @Test
    void getReplyByThreadId_ok() {
        Thread thread = new Thread();
        thread.setId(1L);
        replyService.getReplyByThreadId(thread.getId());
        verify(replyRepository, times(1)).findByThreadId(anyLong());
    }


}
