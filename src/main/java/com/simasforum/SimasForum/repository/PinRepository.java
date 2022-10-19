package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Pin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PinRepository extends CrudRepository<Pin, Long> {
    Pin findByThreadId(Long id);
    Pin findByReplyId(Long id);
    void deleteByThreadId(Long id);
    Pin findPinByThreadIdAndReplyId(Long threadId,Long replyId);
    void deletePinByReplyId(Long id);
    void deletePinByThreadIdAndReplyId(Long threadId,Long replyId);
    void deleteByDateBefore(LocalDate date);
}
