package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Report;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ReportService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ReportController {
    private ReportService reportService;
    private ThreadService threadService;
    private ReplyService replyService;
    private UserService userService;


    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setReplyService(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/thread/report/{id}")
    public String reportThread(@PathVariable("id") Long threadId,
                               @RequestParam("reasonForReport") String reasonForReport,
                               HttpSession session) {
        Optional<Thread> thread = threadService.getThreadDetail(threadId);
        User user = getUserFromSession(session);
        if(user == null) {
            return "redirect:/user/login";
        }
        reportService.addReport(new Report(reasonForReport, thread.get(), user));
        return "redirect:/thread/" + thread.get().getId();
    }
    
    @PostMapping("/thread/reply/report/{id}")
    public String reportReply(@PathVariable("id") Long replyId,
                              @RequestParam("reasonForReport") String reasonForReport,
                              HttpSession session) {
        Optional<Reply> reply = replyService.getReplyById(replyId);
        User user = getUserFromSession(session);
        if(user == null) {
            return "redirect:/user/login";
        }
        reportService.addReport(new Report(reasonForReport, reply.get(), user));
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
