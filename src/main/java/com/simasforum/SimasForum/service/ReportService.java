package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Report;
import com.simasforum.SimasForum.repository.ReportRepository;
import com.simasforum.SimasForum.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report addReport(Report report) {
        return reportRepository.save(report);
    }

    public Map<String, Object> reportReason() {
        List<Report> allThread = reportRepository.findByThreadIsNotNull();
        List<Report> allReply = reportRepository.findByReplyIsNotNull();
        ArrayList<Report> reportThread = new ArrayList<>();
        ArrayList<Report> reportReply = new ArrayList<>();
        Map<Long, ArrayList<String>> dataThread = new HashMap<>();
        Map<Long, ArrayList<String>> dataReply = new HashMap<>();
        for (Report report : allThread) {
            if (dataThread.get(report.getThread().getId()) == null) {
                ArrayList<String> list = new ArrayList<>();
                list.add(report.getReasonForReport());
                dataThread.put(report.getThread().getId(), list);
                reportThread.add(report);
            } else {
                dataThread.get(report.getThread().getId()).add(report.getReasonForReport());
            }
        }
        for (Report report : allReply) {
            if (dataReply.get(report.getReply().getId()) == null) {
                ArrayList<String> list = new ArrayList<>();
                list.add(report.getReasonForReport());
                dataReply.put(report.getReply().getId(), list);
                reportReply.add(report);
            } else {
                dataReply.get(report.getReply().getId()).add(report.getReasonForReport());
            }
        }
        return Map.of("reportListThread", reportThread, "reportReasonsThread", dataThread, "reportListReply", reportReply, "reportReasonsList", dataReply);
    }

    public void acceptReportById(Long id) {
        reportRepository.deleteById(id);
    }


    public void ignoreReportById(Long id) {
        reportRepository.deleteById(id);
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
