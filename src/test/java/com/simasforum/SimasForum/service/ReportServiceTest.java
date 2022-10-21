package com.simasforum.SimasForum.service;


import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.*;
import com.simasforum.SimasForum.repository.ReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @MockBean
    private ReportRepository reportRepository;

    @Test
    void addReport_thread() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User axel = new User("axel", "axel@mail.com", "1234", new Role("user"));
        axel.setId(1L);
        Thread thread = new Thread(axel, "Judul", "Konten", 0, date);
        thread.setId(1L);
        Report report = new Report("Thread mengandung konten SARA", thread, axel);
        reportService.addReport(report);
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void addReport_reply() {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User axel = new User("axel", "axel@mail.com", "1234", new Role("user"));
        axel.setId(1L);
        Thread thread = new Thread(axel, "Judul", "Konten", 0, date);
        thread.setId(1L);
        Reply reply = new Reply("Thread jadul", "Konten", axel, thread);
        Report report = new Report("Thread mengandung konten SARA", reply, axel);
        reportService.addReport(report);
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void getReportByThreadId() {
        reportService.getReportByThreadId(1L);
        verify(reportRepository, times(1)).findByThreadId(anyLong());
    }

    @Test
    void getReportByReplyId() {
        reportService.getReportByReplyId(1L);
        verify(reportRepository, times(1)).findByReplyId(anyLong());
    }

    @Test
    void acceptReportById() {
        reportService.acceptReportById(1L);
        verify(reportRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void ignoreReportReplyById() {
        reportService.ignoreReportReplyById(1L);
        verify(reportRepository, times(1)).deleteByReplyId(anyLong());
    }

    @Test
    void ignoreReportThreadById() {
        reportService.ignoreReportThreadById(1L);
        verify(reportRepository, times(1)).deleteByThreadId(anyLong());
    }

    @Test
    void reportReason() {
        User user = new User();
        Thread thread1 = new Thread();
        thread1.setId(1L);
        Reply reply1 = new Reply();
        reply1.setId(1L);
        when(reportRepository.findByThreadIsNotNull()).thenReturn(List.of(new Report("reason", thread1, user), new Report("reason", thread1, user)));
        when(reportRepository.findByReplyIsNotNull()).thenReturn(List.of(new Report("reason", reply1, user), new Report("reason", reply1, user)));
        Map<String, Object> result = reportService.reportReason();
        Assertions.assertNotNull(result.get("reportListThread"));
        Assertions.assertNotNull(result.get("reportReasonsThread"));
        Assertions.assertNotNull(result.get("reportListReply"));
        Assertions.assertNotNull(result.get("reportReasonsList"));
    }
}
