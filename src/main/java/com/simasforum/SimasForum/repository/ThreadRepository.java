package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Thread;
import liquibase.pro.packaged.T;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
	List<Thread> findByOrderByDatepostDesc();
	List<Thread> findByOrderByUpvoteDesc();
}
