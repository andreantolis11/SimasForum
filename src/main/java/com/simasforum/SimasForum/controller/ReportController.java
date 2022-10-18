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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/thread/report/{threadId}/{userId}")
    public String newReport(@PathVariable Long threadId,
                            @PathVariable Long userId,
                            @RequestParam("alasan") String alasan){

        Optional<Thread> foundThread = threadService.getThreadDetail(threadId);
        User foundUser = userService.getUserById(userId);
        if(foundUser == null) {
            return "redirect:/user/login";
        }
        reportService.addReport(new Report(alasan, foundThread.get(), foundUser));
        return "redirect:/thread/" + foundThread.get().getId();
    }

    @PostMapping("/thread/reply/report/{id}")
    public String reportReply(@PathVariable Long replyId,
                              @PathVariable Long userId,
                              @RequestParam("alasan") String alasan) {
        Optional<Reply> reply = replyService.getReplyById(replyId);
        User foundUser = userService.getUserById(userId);
        if(foundUser == null) {
            return "redirect:/user/login";
        }
        reportService.addReport(new Report(alasan, reply.get(), foundUser));
        return "redirect:/thread/" + reply.get().getThreadId();
    }

    @GetMapping("/reports")
    public String allReports(Model model) {
        List<Report> getData = reportService.allReports();
        model.addAttribute("reports", getData);
        return "reports";
    }

    @PostMapping("/reports/{id}")
    public String acceptReport(@PathVariable Long id) {
        reportService.acceptReportById(id);
        return "redirect:/reports";
    }
}
