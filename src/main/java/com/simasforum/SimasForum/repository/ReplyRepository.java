package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends CrudRepository<Reply,Long> {
     List<Reply> findByThreadId(Long threadId);
     void deleteById(Long id);

}
