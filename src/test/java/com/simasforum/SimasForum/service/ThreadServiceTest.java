package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.ThreadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ThreadServiceTest {

    @Autowired
    private ThreadService threadService;

    @MockBean
    private ThreadRepository threadRepository;

    @Test
    void addThreadItem_ok() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User user = new User();
        user.setId(2L);
        Thread freshThread = new Thread(user, "Fitur Simas+", "Fitur fitur yang dimiliki oleh Simas+", 412, date);
        when(threadRepository.save(any(Thread.class))).thenReturn(freshThread);

        Thread savedThread = threadService.addThread(freshThread);
        assertEquals(2L, savedThread.getUser().getId());
    }

    @Test
    void getThreadItemById_existingThread_ok() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User user = new User();
        user.setId(13L);
        Thread freshThread = new Thread(user, "Fitur Simas+", "Fitur fitur yang dimiliki oleh Simas+", 412, date);
        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(freshThread));
        Optional<Thread> threadtById = threadService.getThreadDetail(freshThread.getId());
        System.out.println(threadtById.get());
    }

    @Test
    void getThreadBySearch() {
        //[SETUP]
        User user = new User();
        user.setId(0L);
        List<Thread> thread = List.of(new Thread(user, "Apa itu investasi", "Content 1", 0, null));
        when(threadRepository.findByTitleContainsIgnoreCase(anyString())).thenReturn(Optional.of(thread).get());

        //[EXERCISE]
        Thread thread1 = threadService.getThreadBySearch("investasi").get(0);

        //[VERIFY]
        verify(threadRepository, times(1)).findByTitleContainsIgnoreCase(anyString());
        assertFalse(thread1.getTitle().isEmpty());
    }

    @Test
    void sortByUpVote() {
        User user = new User();
        user.setId(0L);
        List<Thread> thread = List.of(new Thread(user, "Title 1", "Content 1", 2, null), new Thread(user, "Title 1", "Content 1", 3, null), new Thread(user, "Title 1", "Content 1", 6, null));
        when(threadRepository.findByOrderByVoteScoreDesc()).thenReturn(Optional.of(thread).get());
        Thread threadByVote = threadService.sortByVoteScore().get(2);
        assertEquals(threadByVote.getVoteScore(), 6);
    }

    @Test
    void sortByDate() {
        User user = new User();
        user.setId(0L);
        List<Thread> thread = List.of(new Thread(user, "Title 1", "Content 1", 1, LocalDate.now()), new Thread(user, "Title 1", "Content 1", 2, LocalDate.now()), new Thread(user, "Title 1", "Content 1", 5, LocalDate.now()));
        when(threadRepository.findByOrderByDatePostDesc()).thenReturn(Optional.of(thread).get());
        Thread threadDate = threadService.sortByDate().get(1);
//    	System.out.println(threadVote);
        assertEquals(threadDate.getDatePost(), LocalDate.now());
    }

    @Test
    void getThreadDetail() {
        User user = new User();
        user.setId(0L);
        Optional<Thread> thread = Optional.of(new Thread(user, "Title 1", "Content 1", 1, LocalDate.now()));
        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(thread).get());
        Optional<Thread> threadById = threadService.getThreadDetail(1L);
//    	System.out.println(threadById);
        assertFalse(threadById.isEmpty());
    }

    @Test
    void upVoteReply() {
        User user = new User();
        user.setId(0L);
        Optional<Thread> thread = Optional.of(new Thread(user, "Title 1", "Content 1", 1, LocalDate.now()));
        when(threadRepository.findById(anyLong())).thenReturn((Optional.of(thread)).get());
        threadService.upVoteReply(thread.get().getId());
        assertEquals(2, thread.get().getVoteScore());
    }

    @Test
    void downVoteReply() {
        User user = new User();
        user.setId(0L);
        Optional<Thread> thread = Optional.of(new Thread(user, "Title 2", "Content 2", 31, LocalDate.now()));
        when(threadRepository.findById(anyLong())).thenReturn((Optional.of(thread)).get());
        threadService.downVoteReply(thread.get().getId());
        assertEquals(30, thread.get().getVoteScore());
    }
}
