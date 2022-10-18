package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Report;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReportService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private UserService userService;

    @PostMapping("/thread/report/{threadId}/{userId}")
    public String newReport(@PathVariable Long threadId,
                            @PathVariable Long userId,
                            @RequestParam("alasan") String alasan){

        Optional<Thread> foundThread = threadService.getThreadDetail(threadId);
        User foundUser = userService.getUserById(userId);
        reportService.addReport(new Report(alasan, foundThread.get(), foundUser));

        return "redirect:/dashboard";
    }
}
