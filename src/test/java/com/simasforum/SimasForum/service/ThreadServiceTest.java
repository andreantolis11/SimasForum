package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Thread;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.simasforum.SimasForum.repository.ThreadRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ThreadServiceTest {

    @Autowired
    private ThreadService threadService;

    @MockBean
    private ThreadRepository threadRepository;

    @Test
    void addThreadItem_ok() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        Thread freshThread = new Thread(1, 2, "Fitur Simas+", "Fitur fitur yang dimiliki oleh Simas+", 412, 12, date);
        when(threadRepository.save(any(Thread.class))).thenReturn(freshThread);

        Thread savedThread = threadService.addThread(freshThread);
        assertEquals(2, savedThread.getUser_id());
    }

    @Test
    void getThreadItemById_existingThread_ok() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        Thread freshThread = new Thread(8L, 13, "Fitur Simas+", "Fitur fitur yang dimiliki oleh Simas+", 412, 12, date);
        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(freshThread));
        Thread threadtById = threadService.getThreadtById(freshThread.getId());
        assertEquals(13, threadtById.getUser_id());
    }


}
