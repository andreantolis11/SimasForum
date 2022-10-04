package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThreadController {
    private ThreadService threadService;

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/thread")
    public String threadAll(Thread thread, Model model) {
        model.addAttribute("thread", thread);

        return "/thread";
    }
}
