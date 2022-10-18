package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Pin;
import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Vote;
import com.simasforum.SimasForum.repository.PinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

//    public List<Pin> getPinReply(List<Reply> replies,Long threadId){
//        List<Pin> listPin = new ArrayList<Pin>();
//        for (Reply reply: replies){
////            Pin pin = pinRepository.findByReplyId(reply.getId());
//              Pin pin = pinRepository.findByThreadId(threadId);
//            if(pin!=null){
//                listPin.add(pin);
//            }
//        }
//        return listPin;
//    }

    public Map<Long, Boolean> getPinReply(List<Reply> replies, Long threadId) {
        //Map<replyId, isUpVote>
        Map<Long, Boolean> listPin = new HashMap<Long, Boolean>();
        for (Reply reply : replies) {
            Pin pin = pinRepository.findByReplyIdAndThreadId(reply.getId(),threadId);
            if(pin!=null){
                listPin.put(pin.getReply().getId(),pin.isPin());
            }
        }
        return listPin;
    }
}
