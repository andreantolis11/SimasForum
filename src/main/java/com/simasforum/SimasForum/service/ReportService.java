package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Report;
import com.simasforum.SimasForum.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report addReport(Report report){
        return reportRepository.save(report);
    }
}
