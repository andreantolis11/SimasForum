package com.simasforum.SimasForum.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "thread")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userid")
    private User user;
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
    	this.user = thread.getUser();
    	this.title = thread.getTitle();
        this.content = thread.getContent();
        this.upvote = thread.getDownvote();
        this.downvote = thread.getDownvote();
        this.datepost = thread.getDatepost();
    }

    public Thread(User user, String title, String content, int up_vote, int down_vote, LocalDate date_post) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.upvote = up_vote;
        this.downvote = down_vote;
        this.datepost = date_post;
    }

    public Thread() {
        
    }
}
