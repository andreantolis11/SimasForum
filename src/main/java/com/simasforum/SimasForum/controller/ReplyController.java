package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import liquibase.pro.packaged.E;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
public class ReplyController {
    private ReplyService replyService;
    private UserService userService;
    private ThreadService threadService;

    @Autowired
    public void setReplyService(ReplyService replyService) {
        this.replyService = replyService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    @PostMapping("/reply/thread/{id}")
    public String replyToThread(@PathVariable("id") Long threadId,
                                @RequestParam("content") String content,
                                HttpSession session) {
        Optional<Thread> thread = threadService.getThreadDetail(threadId);
        User user = getUserFromSession(session);
        replyService.addReply(new Reply(thread.get().getUser().getName(), content, user, thread.get()));
        return "redirect:/thread/" + thread.get().getId();
    }

    @PostMapping("/reply/reply/{id}")
    public String replyToReply(@PathVariable("id") Long replyId,
                               @RequestParam("content") String content,
                               HttpSession session) {
        Optional<Reply> reply = replyService.getReplyById(replyId);
        User user = getUserFromSession(session);
        if(user == null) {
            return "redirect:/user/login";
        }
        replyService.addReply(new Reply(reply.get().getUser().getName(), content, user, reply.get()));
        return "redirect:/thread/" + reply.get().getThreadId();
    }

    private User getUserFromSession(HttpSession session) {
        try {
            return userService.getUserById(Long.parseLong(session.getAttribute("USER_LOGIN_ID").toString()));
        }catch(Exception e) {
            return null;
        }
    }
}
