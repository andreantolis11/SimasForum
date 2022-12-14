package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.repository.ReplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    private static final Logger LOG = LoggerFactory.getLogger(ReplyService.class);

    private ReplyRepository replyRepository;
    private PinService pinService;

    @Autowired
    public void setReplyRepository(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }
    @Autowired
    public void setPinService(PinService pinService) {
        this.pinService = pinService;
    }
    public Reply addReply(Reply reply) {
        return replyRepository.save(reply);
    }

    public Optional<Reply> getReplyById(Long id) {
        return replyRepository.findById(id);
    }
    public List<Reply> getReplyByThreadId(Long id) {
        return replyRepository.findByThreadId(id);
    }

    @Transactional
    public Boolean deleteReplyById(Long id) {
        Optional<Reply> toDelete = replyRepository.findById(id);
        if (toDelete.isPresent()) {
            pinService.onDeleteReply(id);
            replyRepository.delete(toDelete.get());
        } else {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
