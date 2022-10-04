package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ThreadService {

    private ThreadRepository threadRepository;

    @Autowired
    public void setThreadRepository(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public Thread addThread(Thread thread) {
        Thread list = new Thread(thread);

        return threadRepository.save(list);
    }

    public Optional<Thread> getThreadDetail(Long id) {
        Optional<Thread> result = threadRepository.findById(id);

        return result;
    }

}
