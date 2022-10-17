package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import com.simasforum.SimasForum.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class VoteController {

    @Autowired
    private ThreadService threadService;
    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/thread/{threadId}/{isUpVote}")
    public String addThreadVote(@PathVariable("threadId") Long threadId,
                                @PathVariable("isUpVote") boolean isUpVote,
                                HttpServletRequest request) {
        try {
            User user = getUserFromSession(request.getSession());
            Optional<Thread> thread = threadService.getThreadDetail(threadId);
            voteService.addThreadVote(thread.get(), user, isUpVote);
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }catch (Exception e){
            return "redirect:/user/login";
        }
    }

    @PostMapping("/thread/{threadId}/{replyId}/{isUpVote}")
    public String addReplyVote(@PathVariable("threadId") Long threadId,
                               @PathVariable("replyId") Long replyId,
                                @PathVariable("isUpVote") boolean isUpVote,
                                HttpServletRequest request) {
        try {
            User user = getUserFromSession(request.getSession());
            Reply reply = replyService.getReplyById(replyId).get();
            voteService.addReplyVote(reply, user, isUpVote);
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }catch (Exception e){
            return "redirect:/user/login";
        }
    }

    private User getUserFromSession(HttpSession session) {
        return userService.getUserById(Long.parseLong(session.getAttribute("USER_LOGIN_ID").toString()));
    }
}
