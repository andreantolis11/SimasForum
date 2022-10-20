package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Pin;
import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.PinRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PinServiceTest {

    @Autowired
    private PinService pinService;

    @MockBean
    private PinRepository pinRepository;


    @Test
    void pinThread_ok() {
        Thread thread = new Thread();
        pinService.pinThread(thread);
        verify(pinRepository, times(1)).save(any(Pin.class));
    }

    @Test
    void unpinThread_ok() {
        Thread thread = new Thread();
        Pin pin = new Pin();
        when(pinRepository.findByThreadId(anyLong())).thenReturn(pin);
        pinService.pinThread(thread);
        verify(pinRepository, times(1)).delete(any(Pin.class));
    }

    @Test
    void mapPinnedThread_ok() {
        LocalDate date = LocalDate.now();
        Thread threadPinned = new Thread();
        threadPinned.setId(1L);
        Pin pin = new Pin(threadPinned, true, date);
        List<Thread> threads = new ArrayList<>();
        threads.add(threadPinned);
        threads.add(new Thread());
        when(pinRepository.findByThreadId(1L)).thenReturn(pin);
        Map<String, List<Thread>> result = pinService.mapPinnedThread(threads);
        Assertions.assertEquals(1, result.get("pinnedThreads").size());
        Assertions.assertEquals(1, result.get("threadList").size());
    }

    @Test
    void pinReply_ok() {
        Reply reply = new Reply();
        pinService.pinReply(reply);
        verify(pinRepository, times(1)).save(any(Pin.class));
    }

    @Test
    void unpinReply_ok() {
        Reply reply = new Reply();
        Pin pin = new Pin();
        when(pinRepository.findByReplyId(anyLong())).thenReturn(pin);
        pinService.pinReply(reply);
        verify(pinRepository, times(1)).delete(any(Pin.class));
    }

    @Test
    void getPinReply_ok() {
        Reply reply = new Reply();
        reply.setId(1L);
        Pin pin = new Pin(reply, true);
        List<Reply> replies = new ArrayList<>();
        replies.add(reply);
        replies.add(new Reply());
        when(pinRepository.findByReplyId(1L)).thenReturn(pin);

        Map<Long, Boolean> predict = Map.of(1L, true);
        Map<Long, Boolean> result = pinService.getPinReply(replies);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(predict, result);


    }
}
