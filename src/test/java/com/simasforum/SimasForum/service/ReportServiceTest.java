package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Report;
import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @MockBean
    private ReportRepository reportRepository;

    @Test
    void addReport_ok(){
        LocalDate date = LocalDate.of(2020, 1, 8);
        Role user = new Role("user");
        user.setId(1L);
        User john = new User("john", "john@gmail.com", "john12345", user);
        john.setId(1L);
        Thread thread = new Thread(john, "Title Spam", "Content Spam", 0, date);
        Report report = new Report("Spam", thread, john);

        when(reportRepository.save(any(Report.class))).thenReturn(report);

        Report savedReport = reportService.addReport(report);
        assertEquals(1L, savedReport.getUser().getId());
    }
}
