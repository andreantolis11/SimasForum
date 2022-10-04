package com.simasforum.SimasForum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.simasforum.SimasForum.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
