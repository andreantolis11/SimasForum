package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

    private ThreadRepository threadRepository;

    @Autowired
    public void setThreadRepository(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }


}
