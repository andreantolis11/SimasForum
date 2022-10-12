package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.repository.ReplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReplyService {

    private static final Logger LOG = LoggerFactory.getLogger(ReplyService.class);

    private ReplyRepository replyRepository;

    @Autowired
    public void setReplyRepository(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }
    public Reply addReply(Reply reply){
        return replyRepository.save(reply);
    }

    public Optional<Reply> getReplyByThreadId(Long id){
        Optional<Reply> reply = replyRepository.findById(id);
        return reply;
    }
}
