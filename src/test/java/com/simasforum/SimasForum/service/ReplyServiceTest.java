package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @MockBean
    private ReplyRepository replyRepository;

    @Test
    void addReplyItem_ok() {
        Reply mockReply = new Reply(2,3,2,5,"test","test",3,5);
        when(replyRepository.save(any(Reply.class))).thenReturn(mockReply);

        Reply savedReply = replyService.addReply(mockReply);
        assertEquals(2, savedReply.getUserid());
    }


}
