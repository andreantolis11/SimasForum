package com.simasforum.SimasForum.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String reasonForReport;

    @ManyToOne
    private Thread thread;
    @ManyToOne
    private Reply reply;
    @ManyToOne
    private User user;

    public Report() {}

    public Report(String reasonForReport, Thread thread, User user) {
        this.reasonForReport = reasonForReport;
        this.thread = thread;
        this.user = user;
    }

    public Report(String reasonForReport, Reply reply, User user) {
        this.reasonForReport = reasonForReport;
        this.reply = reply;
        this.user = user;
    }
}
