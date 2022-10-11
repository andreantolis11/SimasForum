package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ThreadController {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadController.class);
    private ThreadService threadService;
    private UserService userService;

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/thread")
//    public String threadAll(Thread thread, Model model) {
//        model.addAttribute("thread", thread);
//
//        return "thread";
//    }

    @GetMapping("/thread/add")
    public String newThread(Model model, User user, HttpSession session) {
        model.addAttribute("USER_LOGIN_NAME", session.getAttribute("USER_LOGIN_NAME"));
        return "add_thread";
    }

    @PostMapping("/thread/add")
    public String newThread(@RequestParam("title") String title, @RequestParam("content") String content, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getSession().getAttribute("USER_LOGIN_ID").toString());
        threadService.addThread(new Thread(userId, title, content, 0, 0, LocalDate.now()));
        return "redirect:/dashboard";
    }


    @GetMapping("/dashboard")
    public String threadbyDate(Model model, HttpSession session) {
        List<Thread> threadByDate = new ArrayList<>(threadService.sortByDate());
        model.addAttribute("threadbydate", threadByDate);
        List<Thread> threadByVote = new ArrayList<>(threadService.sortByUpVote());
        model.addAttribute("threadbyvote", threadByVote);
        model.addAttribute("USER_LOGIN_NAME", session.getAttribute("USER_LOGIN_NAME"));
        return "dashboard";
    }

    @GetMapping("/thread/{id}")
    public String getThreadDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Thread> threadDetail = threadService.getThreadDetail(id);
        User owner = userService.getUserById(threadDetail.get().getUserid());

        model.addAttribute("threadDetail", threadDetail.get());
        model.addAttribute("userName", owner.getName());
        model.addAttribute("USER_LOGIN_NAME", session.getAttribute("USER_LOGIN_NAME"));
        return "thread";
    }

    @PostMapping("/thread/{id}/{isUpVote}")
    public String addUpVote(@PathVariable("id") Long id, @PathVariable("isUpVote") boolean isUpVote, HttpServletRequest request) {
        try {
            Long.parseLong(request.getSession().getAttribute("USER_LOGIN_ID").toString());
            threadService.addUpVote(id, isUpVote);
            return "redirect:/thread/{id}";
        }catch (Exception e){
            return "login";
        }
    }


//    @PostMapping("/thread")
//    public String newThread(@RequestParam("thread_item") Thread thread) {
//        Thread saved = threadService.addThread(thread);
//
//        return redirectToList(saved.getId());
//    }

    @PostMapping("/thread/search")
    public String getThreadByTitle(@RequestParam("title") String title, Model model) {
        List<Thread> threads = threadService.getThreadBySearch(title);
        System.out.println(threads);
        model.addAttribute("listSearchThreads", threads);
        return "search_thread_result";
    }

    @GetMapping("/thread/search")
    public String getThreadByTitleThreads(@RequestParam("title") String title, Model model, HttpSession session) {
        List<Thread> threads = threadService.getThreadBySearch(title);
        model.addAttribute("listSearchThreads", threads);
        model.addAttribute("USER_LOGIN_NAME", session.getAttribute("USER_LOGIN_NAME"));
        return "search_thread_result";
    }

    @GetMapping("/")
    public String defaultRedirect() {
        return "redirect:/dashboard";
    }
}
