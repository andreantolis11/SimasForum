package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Report;
import com.simasforum.SimasForum.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report addReport(Report report){
        return reportRepository.save(report);
    }

    public List<Report> getReportByThreadId(Long id) {
        List<Report> report = reportRepository.findByThreadId(id);
        return report;
    }

    public List<Report> getReportByReplyId(Long id) {
        List<Report> report = reportRepository.findByReplyId(id);
        return report;
    }
}
