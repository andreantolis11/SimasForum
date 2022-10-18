package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Pin;
import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.repository.PinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PinService {
    @Autowired
    private PinRepository pinRepository;

    public void pinReply(Reply reply){
        Pin pin = pinRepository.findByReplyId(reply.getId());
        if(pin != null){
            pinRepository.save(new Pin(reply,true));
        }else {
            pinRepository.deletePinByReplyId(reply.getId());
        }
    }
}
