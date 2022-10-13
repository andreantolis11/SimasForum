package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.ReplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    private static final Logger LOG = LoggerFactory.getLogger(ReplyService.class);

    private ReplyRepository replyRepository;

    @Autowired
    public void setReplyRepository(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public Reply addReply(Reply reply) {
        return replyRepository.save(reply);
    }

    public Optional<Reply> getReplyById(Long id) {
        return replyRepository.findById(id);
    }
    public List<Reply> getReplyByThreadId(Long id) {
        List<Reply> reply = replyRepository.findByThreadId(id);
        return reply;
    }

    public void upVoteReply(Long id) {
        Optional<Reply> reply = replyRepository.findById(id);
        int upvote = reply.get().getVoteScore();
        reply.get().setVoteScore(upvote + 1);
    }

    public void downVoteReply(Long id) {
        Optional<Reply> reply = replyRepository.findById(id);
        int downvote = reply.get().getVoteScore();
        reply.get().setVoteScore(downvote - 1);
    }

    public Boolean deleteReplyById(Long id) {
        Optional<Reply> toDelete = replyRepository.findById(id);
        if (toDelete.isPresent()) {
            replyRepository.delete(toDelete.get());
        } else {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
