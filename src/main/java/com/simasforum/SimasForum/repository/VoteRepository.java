package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
}
