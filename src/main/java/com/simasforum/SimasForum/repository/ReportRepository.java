package com.simasforum.SimasForum.repository;

import com.simasforum.SimasForum.model.Report;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Long> {
    Report findByThreadIdAndUserId(Long threadId, Long userId);
}
