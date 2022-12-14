package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Report;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.ReplyRepository;
import com.simasforum.SimasForum.repository.ThreadRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
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

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ReplyRepository replyRepository;

    private static final String REDIRECT_REPORT = "redirect:/reports";

    @PostMapping("/thread/report/{threadId}/{userId}")
    public String reportThread(@PathVariable Long threadId,
                            @PathVariable Long userId,
                            @RequestParam("alasan") String alasan,
                           HttpServletRequest request){

        Optional<Thread> foundThread = threadService.getThreadDetail(threadId);
        User foundUser = userService.getUserById(userId);
        if(foundUser == null) {
            return "redirect:/user/login";
        }
        reportService.addReport(new Report(alasan, foundThread.get(), foundUser));
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @PostMapping("/thread/reply/report/{replyId}/{userId}")
    public String reportReply(@PathVariable Long replyId,
                              @PathVariable Long userId,
                              @RequestParam("alasanReply") String alasan) {
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
        Map<String,Object> reports = reportService.reportReason();
        model.addAttribute("reportsThread", reports.get("reportListThread"));
        model.addAttribute("reasonsThread", reports.get("reportReasonsThread"));
        model.addAttribute("reportsReply", reports.get("reportListReply"));
        model.addAttribute("reasonsReply", reports.get("reportReasonsList"));
        return "reports";
    }

    @PostMapping("/reports/thread/ignore/{id}")
    public String ignoreReport(@PathVariable Long id) {
        reportService.ignoreReportThreadById(id);
        return REDIRECT_REPORT;
    }

    @PostMapping("/reports/reply/ignore/{id}")
    public String ignoreReportReply(@PathVariable Long id) {
        reportService.ignoreReportReplyById(id);
        return REDIRECT_REPORT;
    }

    @PostMapping("/reports/accept/{id}/thread/{threadId}")
    public String acceptReport(@PathVariable Long id, @PathVariable Long threadId) {
        reportService.acceptReportById(id);
        threadService.deleteMyThreadById(threadId);
        return REDIRECT_REPORT;
    }

    @PostMapping("/reports/accept/{id}/reply/{replyId}")
    public String acceptReportReply(@PathVariable Long id, @PathVariable Long replyId) {
        reportService.acceptReportById(id);
        replyService.deleteReplyById(replyId);
        return REDIRECT_REPORT;
    }
}
