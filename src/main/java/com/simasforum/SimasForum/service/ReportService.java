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

    public void addReport(Report report){
        reportRepository.save(report);
    }

    public List<Report> allReports() {
        return reportRepository.findAll();
    }
}
