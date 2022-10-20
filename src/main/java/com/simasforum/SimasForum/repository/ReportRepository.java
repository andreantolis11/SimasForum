package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Report;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Long> {
    List<Report> findByThreadId(Long id);
    List<Report> findByReplyId(Long id);

    List<Report> findByThreadIsNotNull();
    List<Report> findByReplyIsNotNull();

    void deleteByThreadId(Long id);
    void deleteByReplyId(Long id);
    void deleteById(Long id);
}
