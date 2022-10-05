package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Thread;
<<<<<<< HEAD
=======
import com.simasforum.SimasForum.repository.ThreadRepository;
>>>>>>> staging
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

<<<<<<< HEAD
import com.simasforum.SimasForum.repository.ThreadRepository;

import java.time.LocalDate;
import java.util.ArrayList;
=======
import java.time.LocalDate;
>>>>>>> staging
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
<<<<<<< HEAD
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
=======
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
>>>>>>> staging
import static org.mockito.Mockito.when;

@SpringBootTest
public class ThreadServiceTest {

    @Autowired
    private ThreadService threadService;

    @MockBean
    private ThreadRepository threadRepository;

    @Test
<<<<<<< HEAD
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


=======
    void getThreadBySearch(){
        List<Thread> thread = List.of(new Thread(1L, "Title 1", "Content 1", 0, 0, null));
        when(threadRepository.findByTitleContains(anyString())).thenReturn(Optional.of(thread).get());

        Thread thread1 = threadService.getThreadBySearch("Title 1").get(0);
        assertFalse(thread1.getTitle().isEmpty());
    }
    
    @Test
    void sortByUpVote() {
    	List<Thread> thread = List.of(new Thread(1L, "Title 1", "Content 1", 2, 0, null),new Thread(2L, "Title 1", "Content 1", 3, 0, null),new Thread(3L, "Title 1", "Content 1", 6, 0, null));
        when(threadRepository.findByOrderByUpvoteDesc()).thenReturn(Optional.of(thread).get());
        Thread threadByVote = threadService.sortByUpVote().get(2);
        assertEquals(threadByVote.getUpvote(), 6);
    }
    
    @Test
    void sortByDate() {
    	List<Thread> thread = List.of(new Thread(1L, "Title 1", "Content 1", 1, 0, LocalDate.now()),new Thread(2L, "Title 1", "Content 1", 2, 0, LocalDate.now()),new Thread(3L, "Title 1", "Content 1", 5, 0, LocalDate.now()));
    	when(threadRepository.findByOrderByDatepostDesc()).thenReturn(Optional.of(thread).get());
    	Thread threadDate = threadService.sortByDate().get(1);
//    	System.out.println(threadVote);
    	assertEquals(threadDate.getDatepost(), LocalDate.now());
    }
    
    @Test
    void getThreadDetail() {
    	Optional<Thread> thread = Optional.of(new Thread(1L, "Title 1", "Content 1", 1, 0, LocalDate.now()));
    	when(threadRepository.findById(anyLong())).thenReturn(Optional.of(thread).get());
    	Optional<Thread> threadById = threadService.getThreadDetail(1L);
//    	System.out.println(threadById);
    	assertFalse(threadById.isEmpty());
    }
>>>>>>> staging
}
