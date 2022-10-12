package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Thread;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
    List<Thread> findByTitleContainsIgnoreCase(String title);
	List<Thread> findByOrderByDatePostDesc();
	List<Thread> findByOrderByVoteScoreDesc();
	void deleteById(Long id);
}
