package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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

    @GetMapping("/thread/id")
    public String getThreadDetails(@PathVariable("id") Long id, Model model) {
        Optional<Thread> threadDetail = threadService.getThreadDetail(id);

        return  threadDetail.toString();
    }

    @PostMapping("/list")
    public String newThread(@RequestParam("thread_item") Thread thread) {
        Thread saved = threadService.addThread(thread);

        return redirectToList(saved.getId());
    }

    private String redirectToList(Long id) {
        return String.format("redirect:/list/%d", id);
    }
}