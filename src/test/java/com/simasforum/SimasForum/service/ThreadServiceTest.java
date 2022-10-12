package com.simasforum.SimasForum.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.simasforum.SimasForum.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.ThreadRepository;

@SpringBootTest
public class ThreadServiceTest {

    @Autowired
    private ThreadService threadService;

    @MockBean
    private ThreadRepository threadRepository;

    @Test
    void addThreadItem_ok() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123");
        andre.setId(1L);
        Thread freshThread = new Thread(andre, "Fitur Simas+", "Fitur fitur yang dimiliki oleh Simas+", 412, 12, date);
        when(threadRepository.save(any(Thread.class))).thenReturn(freshThread);

        Thread savedThread = threadService.addThread(freshThread);
        assertEquals(1L, savedThread.getUser().getId());
    }

    @Test
    void getThreadItemById_existingThread_ok() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123");
        andre.setId(1L);
        Thread freshThread = new Thread(andre, "Fitur Simas+", "Fitur fitur yang dimiliki oleh Simas+", 412, 12, date);
        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(freshThread));
        Optional<Thread> threadtById = threadService.getThreadDetail(freshThread.getId());
        System.out.println(threadtById.get());
    }

    @Test
    void getThreadBySearch(){
        //[SETUP]
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123");
        andre.setId(1L);
        List<Thread> thread = List.of(new Thread( andre, "Apa itu investasi", "Content 1", 0, 0, null));
        when(threadRepository.findByTitleContainsIgnoreCase(anyString())).thenReturn(Optional.of(thread).get());

        //[EXERCISE]
        Thread thread1 = threadService.getThreadBySearch("investasi").get(0);

        //[VERIFY]
        verify(threadRepository, times(1)).findByTitleContainsIgnoreCase(anyString());
        assertFalse(thread1.getTitle().isEmpty());
    }

    @Test
    void sortByUpVote() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123");
        andre.setId(1L);
    	List<Thread> thread = List.of(new Thread(andre, "Title 1", "Content 1", 2, 0, null),new Thread(andre, "Title 1", "Content 1", 3, 0, null),new Thread( andre, "Title 1", "Content 1", 6, 0, null));
        when(threadRepository.findByOrderByUpvoteDesc()).thenReturn(Optional.of(thread).get());
        Thread threadByVote = threadService.sortByUpVote().get(2);
        assertEquals(threadByVote.getUpvote(), 6);
    }

    @Test
    void sortByDate() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123");
        andre.setId(1L);
    	List<Thread> thread = List.of(new Thread( andre, "Title 1", "Content 1", 1, 0, LocalDate.now()),new Thread(andre, "Title 1", "Content 1", 2, 0, LocalDate.now()),new Thread( andre, "Title 1", "Content 1", 5, 0, LocalDate.now()));
    	when(threadRepository.findByOrderByDatepostDesc()).thenReturn(Optional.of(thread).get());
    	Thread threadDate = threadService.sortByDate().get(1);
    	assertEquals(threadDate.getDatepost(), LocalDate.now());
    }

    @Test
    void getThreadDetail() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123");
        andre.setId(1L);
    	Optional<Thread> thread = Optional.of(new Thread(andre, "Title 1", "Content 1", 1, 0, LocalDate.now()));
    	when(threadRepository.findById(anyLong())).thenReturn(Optional.of(thread).get());
    	Optional<Thread> threadById = threadService.getThreadDetail(1L);
    	assertFalse(threadById.isEmpty());
    }

    @Test
    void upVoteReply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123");
        andre.setId(1L);
        Optional<Thread> thread = Optional.of(new Thread(andre, "Title 1", "Content 1", 1, 0, LocalDate.now()));
        when(threadRepository.findById(anyLong())).thenReturn((Optional.of(thread)).get());
        threadService.upVoteReply(thread.get().getId());
        assertEquals(2, thread.get().getUpvote());
    }

    @Test
    void downVoteReply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123");
        andre.setId(1L);
        Optional<Thread> thread = Optional.of(new Thread(andre, "Title 2", "Content 2", 31, 31, LocalDate.now()));
        when(threadRepository.findById(anyLong())).thenReturn((Optional.of(thread)).get());
        threadService.downVoteReply(thread.get().getId());
        assertEquals(30, thread.get().getDownvote());
    }

    @Test
    void deleteMyThreadById() {
        threadService.deleteMyThreadById(0L);
        verify(threadRepository, times(1)).deleteById(anyLong());
    }
}
