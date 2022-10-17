package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.model.Vote;
import com.simasforum.SimasForum.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @MockBean
    private VoteRepository voteRepository;

    @Test
    void addUpVote_thread() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123", new Role("user"));
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        when(voteRepository.findByThreadIdAndUserId(anyLong(), anyLong())).thenReturn(null);
        voteService.addThreadVote(thread, fadhlul, true);
        assertEquals(thread.getVoteScore(), 1);
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    void addDownVote_thread() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123", new Role("user"));
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        when(voteRepository.findByThreadIdAndUserId(anyLong(), anyLong())).thenReturn(null);
        voteService.addThreadVote(thread, fadhlul, false);
        assertEquals(thread.getVoteScore(), -1);
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

}
