package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Pin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository extends CrudRepository<Pin, Long> {
    Pin findByThreadId(Long id);
    Pin findByReplyId(Long id);
    void deletePinByThreadId(Long id);
    void deletePinByReplyId(Long id);
}
