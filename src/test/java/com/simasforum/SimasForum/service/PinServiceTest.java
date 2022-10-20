package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Pin;
import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.PinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PinServiceTest {

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
    void pinReply_ok() {
        Reply reply = new Reply();
        Thread thread = new Thread();
        pinService.pinReply(thread,reply);
        verify(pinRepository, times(1)).save(any(Pin.class));
    }

    @Test
    void unpinReply_ok() {
        Reply reply = new Reply();
        Thread thread = new Thread();
        Pin pin = new Pin();
        when(pinRepository.findByReplyId(anyLong())).thenReturn(pin);
        pinService.pinReply(thread,reply);
        verify(pinRepository, times(1)).delete(any(Pin.class));
    }
}
