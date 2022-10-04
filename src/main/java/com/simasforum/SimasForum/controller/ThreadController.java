package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

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

    @GetMapping("/thread/add")
    public String newThread(Model model, User user) {
        model.addAttribute("user_id", 1L);
        return "/add_thread";
    }

    @PostMapping("/thread/add")
    public String newThread(@RequestParam("title") String title, @RequestParam("content") String content){
        threadService.addThread(new Thread(title, content, 0, 0, LocalDate.now()));
        return "/my_thread";
    }
    @GetMapping("/threadbydate")
    public String threadbyDate( Model model){
        List<Thread> threads = new ArrayList<>(threadService.sortByDate());
//        thread  = threadService.sortByDate();
        model.addAttribute("threadbydate", threads);
        return "thread";
    }

    @GetMapping("/thread/{id}")
    public String getThreadDetails(@PathVariable("id") Long id, Model model) {
        Optional<Thread> threadDetail = threadService.getThreadDetail(id);

        return  threadDetail.toString();
    }

    @PostMapping("/thread")
    public String newThread(@RequestParam("thread_item") Thread thread) {
        Thread saved = threadService.addThread(thread);

        return redirectToList(saved.getId());
    }

    private String redirectToList(Long id) {
        return String.format("redirect:/list/%d", id);
    }
}
