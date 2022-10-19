package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Map;
import java.util.Optional;

@Controller
@Transactional
public class ThreadController {

    private ThreadService threadService;
    private UserService userService;
    private ReplyService replyService;
    private VoteService voteService;
    private PinService pinService;

    private static final String THREAD_DETAIL_MODEL = "threadDetail";

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    @Autowired
    public void setReplyService(ReplyService replyService) {
        this.replyService = replyService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }

    @Autowired
    public void setPinService(PinService pinService) {
        this.pinService = pinService;
    }

    @GetMapping("/thread/add")
    public String newThread(Model model, HttpSession session) {
        model.addAttribute("USER_LOGIN_NAME", session.getAttribute("USER_LOGIN_NAME"));

        return "add_thread";
    }

    @PostMapping("/thread/add")
    public String newThread(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            HttpServletRequest request,
                            Model model) {
        User user = getUserFromSession(request.getSession());
        Thread thread = new Thread(user, title, content, 0, LocalDate.now());
        threadService.addThread(thread);
        request.getSession().setAttribute("successMessage", "Inserted Successfully !");
        return "redirect:/dashboard";
    }

    @GetMapping("/thread/{id}/vote")
    public String updateVote(@PathVariable("id") Long id, String method) {
        System.out.println("This one triggered");
        if (method.equals("upVote")) {
            threadService.upVoteThread(id);
        } else {
            threadService.downVoteThread(id);
        }
        return "thread";
    }

    @GetMapping("/dashboard")
    public String threadbyDate(Model model, HttpSession session) {
        List<Thread> threadByDate = new ArrayList<>(threadService.sortByDate());
        Map<String, List<Thread>> dateThreads = pinService.mapPinnedThread(threadByDate);
        model.addAttribute("threadbydate", dateThreads.get("threadList"));
        model.addAttribute("pinnedthreadbydate", dateThreads.get("pinnedThreads"));
        List<Thread> threadByVote = new ArrayList<>(threadService.sortByVoteScore());
        model.addAttribute("threadbyvote", threadByVote);
        return "dashboard";
    }

    @GetMapping("/thread/{id}")
    public String getThreadDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Thread> threadDetail = threadService.getThreadDetail(id);
        List<Reply> reply = threadDetail.get().getReply();
        model.addAttribute("replies", reply);
        model.addAttribute("sizes", reply.size());
        User owner = threadDetail.get().getUser();
        List<Reply> threadReplies = threadDetail.get().getReply();
        Map<Long, Boolean> replyVoteMap = voteService.getUserVotedList(threadReplies, (Long) session.getAttribute("USER_LOGIN_ID"));
        Map<Long, Boolean> replyPin = pinService.getPinReply(threadReplies, id);
        int upVotes = threadService.getVoteByUserAndThreadId(id, (Long) session.getAttribute("USER_LOGIN_ID"));
        model.addAttribute(THREAD_DETAIL_MODEL, threadDetail.get());
        model.addAttribute("threadReplies", threadReplies);
        model.addAttribute("userName", owner.getName());
        model.addAttribute("userId", session.getAttribute("USER_LOGIN_ID"));
        model.addAttribute("USER_LOGIN_NAME", session.getAttribute("USER_LOGIN_NAME"));
        model.addAttribute("upVotes", upVotes);
        model.addAttribute("votesReply", replyVoteMap);
        model.addAttribute("pinsReply", replyPin);

        return "thread";
    }

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

    @GetMapping("/thread/edit/{id}")
    public String getEditPage(@PathVariable("id") Long id, Model model) {
        Optional<Thread> foundThread = threadService.getThreadDetail(id);
        model.addAttribute(THREAD_DETAIL_MODEL, foundThread.get());
        return "edit_thread";
    }

    @PostMapping("/thread/edit/{id}")
    public String editThreadDetails(@PathVariable("id") Long id,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content,
                                    Model model) {
        Optional<Thread> threadDetail = threadService.getThreadDetail(id);
        threadDetail.get().setTitle(title);
        threadDetail.get().setContent(content);
        model.addAttribute(THREAD_DETAIL_MODEL, threadDetail.get());
        return "redirect:/mythread";
    }

    @GetMapping("/mythread")
    public String getMyThread(Model model, HttpSession session) {
        User owner = getUserFromSession(session);
        List<Thread> myThreads = threadService.getAllMyThread(owner);
        model.addAttribute("result", myThreads);
        model.addAttribute("USER_LOGIN_NAME", session.getAttribute("USER_LOGIN_NAME"));
        return "my_thread";
    }

    @PostMapping("/mythread/{id}")
    public String deleteMyThread(@PathVariable("id") Long id) {
        threadService.deleteMyThreadById(id);
        return "redirect:/mythread";
    }

    @PostMapping("/thread/{id}/reply/delete/{replyId}")
    public String deleteReply(@PathVariable("id") Long id,
                              @PathVariable("replyId") Long replyId) {
        replyService.deleteReplyById(replyId);
        return "redirect:/thread/" + id;
    }


    private User getUserFromSession(HttpSession session) {
        return userService.getUserById(Long.parseLong(session.getAttribute("USER_LOGIN_ID").toString()));
    }

//    private void mockInsertReply(Thread thread, User user) {
//        Reply reply = new Reply("reply name", "content reply", user, thread);
//        replyService.addReply(reply);
//        replyService.addReply(new Reply("reply name 2", "content reply2", user, reply));
//        replyService.addReply(new Reply("reply name 3", "content reply3", user, reply));
//        replyService.addReply(new Reply("reply name 4", "content reply4", user, reply));
//    }
}
