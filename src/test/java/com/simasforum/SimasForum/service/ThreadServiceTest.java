package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.ThreadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ThreadServiceTest {

    @Autowired
    private ThreadService threadService;

    @MockBean
    private ThreadRepository threadRepository;

    @Test
    void getThreadBySearch(){
        List<Thread> thread = List.of(new Thread(1L, "Title 1", "Content 1", 0, 0, null));
        when(threadRepository.findByTitleContains(anyString())).thenReturn(Optional.of(thread).get());

        Thread thread1 = threadService.getThreadBySearch("Title 1").get(0);
        assertFalse(thread1.getTitle().isEmpty());
    }
}
