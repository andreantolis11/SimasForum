package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.ThreadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThreadService {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadService.class);
    private static final String TODO_LIST_DOES_NOT_EXIST_FMT = "Thread(id=%d) does not exist";

    private ThreadRepository threadRepository;
    private UserService userService;
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

    public void addUpVote(Long id, boolean isUpVote) {
        Optional<Thread> result = threadRepository.findById(id);
        if(isUpVote){
            result.get().setVoteScore(result.get().getVoteScore() + 1);
        }else{
            result.get().setVoteScore(result.get().getVoteScore() - 1);
        }
    }

    public List<Thread> sortByDate(){
//        List<Thread> threadList = new ArrayList<Thread>();
//        System.out.println(threadList);
//        Thread thread = new Thread(Sort.Direction.DESC,"post_date");
//        threadList.add(thread);

        return (List<Thread>) threadRepository.findByOrderByDatePostDesc();
    }

    public List<Thread> getThreadBySearch(String title){
        return threadRepository.findByTitleContainsIgnoreCase(title);
    }

    public void upVoteReply(Long id) {
        Optional<Thread> thread = threadRepository.findById(id);
        int upvote = thread.get().getVoteScore();
        thread.get().setVoteScore(upvote + 1);
    }

    public void downVoteReply(Long id) {
        Optional<Thread> thread = threadRepository.findById(id);
        int downvote = thread.get().getVoteScore();
        thread.get().setVoteScore(downvote - 1);
    }

    public List<Thread> sortByVoteScore(){
        return  (List<Thread>) threadRepository.findByOrderByVoteScoreDesc();
    }

    public List<Thread> getAllMyThread(User user) {
        List<Thread> myThreads = user.getThread();
        return myThreads;
    }

    public void deleteMyThreadById(Long id) {
        threadRepository.deleteById(id);
    }

}
