package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        Reply mockReply = new Reply("reply 1", "content 11", user, thread);
        replyService.addReply(mockReply);
        verify(replyRepository, times(1)).save(any(Reply.class));
    }

    @Test
    void getReplyByThreadId_ok() {
        Thread thread = new Thread();
        thread.setId(1L);
        replyService.getReplyByThreadId(thread.getId());
        verify(replyRepository, times(1)).findByThreadId(anyLong());
    }

    @Test
    void deleteReplyById_ok() {
        Reply reply = new Reply();
        reply.setId(1L);
        when(replyRepository.findById(anyLong())).thenReturn(Optional.of(reply));
        Boolean result = replyService.deleteReplyById(1L);
        verify(replyRepository, times(1)).delete(any(Reply.class));
        assertEquals(Boolean.TRUE, result);
    }
}
