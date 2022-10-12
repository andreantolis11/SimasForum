package com.simasforum.SimasForum.service;

import java.util.List;
import java.util.Optional;

import com.simasforum.SimasForum.model.Vote;
import com.simasforum.SimasForum.repository.VoteRepository;
import liquibase.pro.packaged.P;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.ThreadRepository;

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
        Optional<Thread> result = threadRepository.findById(id);
        return result;
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
//        return Optional.ofNullable(voteRepository.findByThreadIdAndUserId(threadId, userId));
    }

    public void addUpVote(Long threadId, boolean isUpVote, Long userId) {
        Optional<Thread> result = threadRepository.findById(threadId);
        Vote foundVote = voteRepository.findByThreadIdAndUserId(threadId, userId);
        if (result.isPresent()){
           if (isUpVote){
                try {
                    if (foundVote.isUpVote()){
                        voteRepository.deleteById(foundVote.getId());
                        result.get().setVote(result.get().getVote() - 1);
                    }else {
                        voteRepository.deleteById(foundVote.getId());
                        result.get().setVote(result.get().getVote() + 1);
                        voteRepository.save(new Vote(threadId, 0L, userId, true));
                    }
                }catch (Exception e){
                    voteRepository.save(new Vote(threadId, 0L, userId, true));
                    result.get().setVote(result.get().getVote() + 1);
                }
           }else {
               try {
                   if (!foundVote.isUpVote()){
                       voteRepository.deleteById(foundVote.getId());
                       result.get().setVote(result.get().getVote() + 1);
                   }else {
                       voteRepository.deleteById(foundVote.getId());
                       result.get().setVote(result.get().getVote() - 1);
                       voteRepository.save(new Vote(threadId, 0L, userId, false));
                   }

               }catch (Exception e){
                   voteRepository.save(new Vote(threadId, 0L, userId, false));
                   result.get().setVote(result.get().getVote() - 1);
               }
           }
        }
    }

    public List<Thread> sortByDate(){
//        List<Thread> threadList = new ArrayList<Thread>();
//        System.out.println(threadList);
//        Thread thread = new Thread(Sort.Direction.DESC,"post_date");
//        threadList.add(thread);

        return (List<Thread>) threadRepository.findByOrderByDatepostDesc();
    }

    public List<Thread> getThreadBySearch(String title){
        return threadRepository.findByTitleContainsIgnoreCase(title);
    }
    public List<Thread> sortByUpVote(){
        return  (List<Thread>) threadRepository.findByOrderByVoteDesc();
    }
}
