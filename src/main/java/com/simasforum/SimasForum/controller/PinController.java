package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.service.PinService;
import com.simasforum.SimasForum.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PinController {

    @Autowired
    private PinService pinService;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/thread/{replyId}")
    public String pinReply(@PathVariable("replyId") Long replyId, HttpServletRequest request){
        try{
            Reply reply = replyService.getReplyById(replyId).get();
            pinService.pinReply(reply);
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }catch (Exception e){
            return "login";
        }
    }
}
