package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.service.PinService;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class PinController {

    @Autowired
    private PinService pinService;

    @Autowired
    private ReplyService replyService;
    @Autowired
    private ThreadService threadService;

    @GetMapping("/pin/thread/{threadId}")
    public String pinThread(@PathVariable("threadId") Long threadId, HttpSession session) {
        if (session.getAttribute("USER_LOGIN_ROLE") != null
                && session.getAttribute("USER_LOGIN_ROLE").toString().equals("admin")) {
            Optional<Thread> thread = threadService.getThreadDetail(threadId);
            pinService.pinThread(thread.get());
        }
        return "redirect:/";
    }

    @PostMapping("/pin/reply/{replyId}")
    public String pinReply(@PathVariable("replyId") Long replyId, HttpServletRequest request) {
        try {
            Reply reply = replyService.getReplyById(replyId).get();
            pinService.pinReply(reply);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        } catch (Exception e) {
            return "login";
        }
    }
}
