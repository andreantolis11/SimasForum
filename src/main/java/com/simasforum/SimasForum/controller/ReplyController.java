package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


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
                                HttpSession session, Model model) {
        Thread thread = threadService.getThreadDetail(threadId).get();
        User user = getUserFromSession(session);
        replyService.addReply(new Reply(thread.getUser().getName(), content, user, thread));
        return "redirect:/thread/" + thread.getId();
    }

    @PostMapping("/reply/reply/{id}")
    public String replyToReply(@PathVariable("id") Long replyId,
                               @RequestParam("content") String content,
                               HttpSession session, Model model) {
        Reply reply = replyService.getReplyById(replyId);
        User user = getUserFromSession(session);
        replyService.addReply(new Reply(reply.getUser().getName(), content, user, reply));
        return "redirect:/thread/" + reply.getThreadId();
    }

    private User getUserFromSession(HttpSession session) {
        return userService.getUserById(Long.parseLong(session.getAttribute("USER_LOGIN_ID").toString()));
    }
}
