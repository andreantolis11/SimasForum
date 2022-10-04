package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Thread;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
}
