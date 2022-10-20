package com.simasforum.SimasForum.service;

import java.util.List;
import java.util.Optional;

import com.simasforum.SimasForum.model.Vote;
import com.simasforum.SimasForum.repository.VoteRepository;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

    private ThreadRepository threadRepository;
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    public void setThreadRepository(ThreadRepository threadRepository) {

        this.threadRepository = threadRepository;
    }

    public Thread addThread(Thread thread) {
        return threadRepository.save(thread);
    }

    public Optional<Thread> getThreadDetail(Long id) {
        return threadRepository.findById(id);
    }

    public int getVoteByUserAndThreadId(Long threadId, Long userId){
        try {
           Optional<Vote> vote = Optional.ofNullable(voteRepository.findByThreadIdAndUserId(threadId, userId));
           if (vote.get().isUpVote()){
               return 1;
           }
           return 0;
        }catch (Exception e){
            return -1;
        }
    }

    public List<Thread> sortByDate(){
        return threadRepository.findByOrderByDatePostDesc();
    }

    public List<Thread> getThreadBySearch(String title){
        return threadRepository.findByTitleContainsIgnoreCase(title);
    }


    public void upVoteThread(Long id) {
        Optional<Thread> thread = threadRepository.findById(id);
        int upvote = thread.get().getVoteScore();
        thread.get().setVoteScore(upvote + 1);
    }

    public void downVoteThread(Long id) {
        Optional<Thread> thread = threadRepository.findById(id);
        int downvote = thread.get().getVoteScore();
        thread.get().setVoteScore(downvote - 1);
    }

    public List<Thread> sortByVoteScore(){
        return threadRepository.findByOrderByVoteScoreDesc();
    }

    public List<Thread> getAllMyThread(User user) {
        return user.getThread();
    }

    public void deleteMyThreadById(Long id) {
        threadRepository.deleteById(id);
    }

}
