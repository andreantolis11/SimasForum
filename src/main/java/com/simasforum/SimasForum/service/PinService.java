package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Pin;
import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.repository.PinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PinService {

    private PinRepository pinRepository;

    @Autowired
    public void setPinRepository(PinRepository pinRepository) {
        this.pinRepository = pinRepository;
    }

    public void pinThread(Thread thread) {
        Pin pin = pinRepository.findByThreadId(thread.getId());
        if (pin == null) {
            LocalDate date = LocalDate.now();
            pinRepository.save(new Pin(thread, true, date));
        } else {
            pinRepository.delete(pin);
        }
    }

    public Map<String, List<Thread>> mapPinnedThread(List<Thread> threads) {
        deleteExpiredThreadPin();
        List<Thread> pinnedThreads = new ArrayList<>();
        int index = 0;
        while (index < threads.size()) {
            Pin pin = pinRepository.findByThreadId(threads.get(index).getId());
            if (pin != null) {
                pinnedThreads.add(threads.get(index));
                threads.remove(threads.get(index));
            } else {
                index += 1;
            }
        }
        return Map.of("pinnedThreads", pinnedThreads, "threadList", threads);
    }

    public void deleteExpiredThreadPin() {
        LocalDate tenDaysAgo = LocalDate.now().minusDays(10);
        pinRepository.deleteByDateBefore(tenDaysAgo);
    }

    public void pinReply(Reply reply) {

        Pin pin = pinRepository.findByReplyId(reply.getId());
        if (pin == null) {
            pinRepository.save(new Pin(reply, true));
        } else {
            pinRepository.delete(pin);
        }
    }

    public Map<Long, Boolean> getPinReply(List<Reply> replies) {
        //Map<replyId, isUpVote>
        Map<Long, Boolean> listPin = new HashMap<Long, Boolean>();
        for (Reply reply : replies) {
            Pin pin = pinRepository.findByReplyId(reply.getId());
            if (pin != null) {
                listPin.put(pin.getReplyId(), pin.isPin());
            }
        }
        return listPin;
    }

    public void onDeleteThread(Long id) {
        pinRepository.deleteByThreadId(id);
    }

    public void onDeleteReply(Long id) {
        pinRepository.deleteByReplyId(id);
    }
}
