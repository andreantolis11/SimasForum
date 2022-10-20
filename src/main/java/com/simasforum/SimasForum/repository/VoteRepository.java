package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

    List<Vote> findByThreadId(Long id);
    Vote findByThreadIdAndUserId(Long id, Long userId);
    Vote findByReplyIdAndUserId(Long id, Long userId);
    void deleteByUserId(Long id);

    void deleteAllByThreadId(Long threadId);

}
