package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        assertEquals(1, thread.getVoteScore());
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
        assertEquals(-1, thread.getVoteScore());
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    void cancelUpVote_thread() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Vote vote = new Vote(thread, fadhlul, true);
        vote.setId(1L);
        when(voteRepository.findByThreadIdAndUserId(anyLong(), anyLong())).thenReturn(vote);
        voteService.addThreadVote(thread, fadhlul, true);
        verify(voteRepository, times(1)).deleteById(anyLong());
        assertEquals(-1, thread.getVoteScore());
    }

    @Test
    void cancelDownVote_thread() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Vote vote = new Vote(thread, fadhlul, false);
        vote.setId(1L);
        when(voteRepository.findByThreadIdAndUserId(anyLong(), anyLong())).thenReturn(vote);
        voteService.addThreadVote(thread, fadhlul, false);
        verify(voteRepository, times(1)).deleteById(anyLong());
        assertEquals(1, thread.getVoteScore());
    }

    @Test
    void changeUpVoteToDownVote_thread() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Vote vote = new Vote(thread, fadhlul, true);
        vote.setId(1L);
        when(voteRepository.findByThreadIdAndUserId(anyLong(), anyLong())).thenReturn(vote);
        voteService.addThreadVote(thread, fadhlul, false);
        verify(voteRepository, times(1)).deleteById(anyLong());
        assertEquals(-2, thread.getVoteScore());
    }

    @Test
    void changeDownVoteToUpVote_thread() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Vote vote = new Vote(thread, fadhlul, false);
        vote.setId(1L);
        when(voteRepository.findByThreadIdAndUserId(anyLong(), anyLong())).thenReturn(vote);
        voteService.addThreadVote(thread, fadhlul, true);
        verify(voteRepository, times(1)).deleteById(anyLong());
        assertEquals(2, thread.getVoteScore());
    }

    @Test
    void addUpVote_reply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        Reply reply = new Reply("name", "content", fadhlul, thread);
        thread.setId(1L);
        when(voteRepository.findByReplyIdAndUserId(anyLong(), anyLong())).thenReturn(null);
        voteService.addReplyVote(reply, fadhlul, true);
        assertEquals(1, reply.getVoteScore());
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    void addDownVote_reply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        Reply reply = new Reply("name", "content", fadhlul, thread);
        thread.setId(1L);
        when(voteRepository.findByReplyIdAndUserId(anyLong(), anyLong())).thenReturn(null);
        voteService.addReplyVote(reply, fadhlul, false);
        assertEquals(-1, reply.getVoteScore());
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    void cancelUpVote_reply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Reply reply = new Reply("name", "content", fadhlul, thread);
        Vote vote = new Vote(reply, fadhlul, true);
        vote.setId(1L);
        when(voteRepository.findByReplyIdAndUserId(anyLong(), anyLong())).thenReturn(vote);
        voteService.addReplyVote(reply, fadhlul, true);
        verify(voteRepository, times(1)).deleteById(anyLong());
        assertEquals(-1, reply.getVoteScore());
    }

    @Test
    void cancelDownVote_reply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Reply reply = new Reply("name", "content", fadhlul, thread);
        Vote vote = new Vote(reply, fadhlul, false);
        vote.setId(1L);
        when(voteRepository.findByReplyIdAndUserId(anyLong(), anyLong())).thenReturn(vote);
        voteService.addReplyVote(reply, fadhlul, false);
        verify(voteRepository, times(1)).deleteById(anyLong());
        assertEquals(1, reply.getVoteScore());
    }

    @Test
    void changeUpVoteToDownVote_reply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Reply reply = new Reply("name", "content", fadhlul, thread);
        Vote vote = new Vote(reply, fadhlul, true);
        vote.setId(1L);
        when(voteRepository.findByReplyIdAndUserId(anyLong(), anyLong())).thenReturn(vote);
        voteService.addReplyVote(reply, fadhlul, false);
        verify(voteRepository, times(1)).deleteById(anyLong());
        assertEquals(-2, reply.getVoteScore());
    }

    @Test
    void changeDownVoteToUpVote_reply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Reply reply = new Reply("name", "content", fadhlul, thread);
        Vote vote = new Vote(reply, fadhlul, false);
        vote.setId(1L);
        when(voteRepository.findByReplyIdAndUserId(anyLong(), anyLong())).thenReturn(vote);
        voteService.addReplyVote(reply, fadhlul, true);
        verify(voteRepository, times(1)).deleteById(anyLong());
        assertEquals(2, reply.getVoteScore());
    }

    @Test
    void getUserVotedList_ok() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("Fadhlul", "fad@gmail.com", "123");
        fadhlul.setId(1L);
        Thread thread = new Thread(fadhlul, "Lorem ipsum", "Lorem impus", 0, date);
        thread.setId(1L);
        Reply reply = new Reply("name", "content", fadhlul, thread);
        Reply reply2 = new Reply("name2", "content2", fadhlul, thread);
        reply.setId(1L);
        reply2.setId(2L);
        Vote vote = new Vote(reply, fadhlul, true);
        vote.setId(1L);
        List<Reply> replies = List.of(reply,reply2);
        when(voteRepository.findByReplyIdAndUserId(eq(1L),anyLong())).thenReturn(vote);

        Map<Long, Boolean> predict = Map.of(1L,true);
        Map<Long, Boolean> result = voteService.getUserVotedList(replies, fadhlul.getId());
        assertEquals(predict,result);
    }
}
