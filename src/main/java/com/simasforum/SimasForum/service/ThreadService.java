package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.ThreadRepository;
import liquibase.pro.packaged.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ThreadService {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadService.class);
    private static final String TODO_LIST_DOES_NOT_EXIST_FMT = "Thread(id=%d) does not exist";

    private ThreadRepository threadRepository;

    @Autowired
    public void setThreadRepository(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public Thread getThreadtById(Long id) throws NoSuchElementException {
        Optional<Thread> result = threadRepository.findById(id);

        return result.get();
    }

    public Thread addThread(Thread thread) {
        Thread list = new Thread(thread);

        return threadRepository.save(thread);
    }

    public Optional<Thread> getThreadDetail(Long id) {
        Optional<Thread> result = threadRepository.findById(id);

        return result;
    }

    public List<Thread> sortByDate(){
        List<Thread> threadList = new ArrayList<Thread>();

        Thread thread = new Thread(Sort.Direction.DESC,"post_date");
        threadList.add(thread);

        return threadList;
    }

    public List<Thread> sortByVote(){
//        int sum = 0;
//        Thread thread = new Thread();
        List<Thread> threadList = new ArrayList<Thread>();

        Thread thread = new Thread(Sort.Direction.DESC,"up_vote");
        threadList.add(thread);

        return threadList;
    }
}
