package com.simasforum.SimasForum.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "thread")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private int up_vote;
    private int down_vote;
    @Column(nullable = false)
    private Date date_post;

    public Thread() {}

    public Thread(Thread thread) {
        this.title = thread.getTitle();
        this.content = thread.getContent();
        this.up_vote = thread.getUp_vote();
        this.down_vote = thread.getDown_vote();
        this.date_post = thread.getDate_post();
    }
}
