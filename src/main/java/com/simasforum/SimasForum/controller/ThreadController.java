package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.service.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ThreadController {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadController.class);
    private ThreadService threadService;

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/thread")
    public String threadAll(Thread thread, Model model) {
        model.addAttribute("thread", thread);

        return "thread";
    }

    @GetMapping("/threadbydate")
    public String threadbyDate( Model model){
        List<Thread> threads = new ArrayList<>(threadService.sortByDate());
//        thread  = threadService.sortByDate();
        model.addAttribute("threadbydate", threads);
        return "thread";
    }
}
