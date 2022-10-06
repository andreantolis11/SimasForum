package com.simasforum.SimasForum.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "thread")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userid;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private int upvote;
    private int downvote;
    @Column(nullable = false)
    private LocalDate datepost;

    public Thread(Sort.Direction desc, String post_date) {}

    public Thread(Thread thread) {
    	this.userid = thread.getUserid();
    	this.title = thread.getTitle();
        this.content = thread.getContent();
        this.upvote = thread.getDownvote();
        this.downvote = thread.getDownvote();
        this.datepost = thread.getDatepost();
    }

    public Thread(long userid, String title, String content, int up_vote, int down_vote, LocalDate date_post) {
        this.userid = userid;
        this.title = title;
        this.content = content;
        this.upvote = up_vote;
        this.downvote = down_vote;
        this.datepost = date_post;
    }

    public Thread() {
        
    }
}
