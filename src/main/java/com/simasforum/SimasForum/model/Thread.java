package com.simasforum.SimasForum.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "thread")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "threadId")
    private List<Reply> reply;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private int voteScore;
    @Column(nullable = false)
    private LocalDate datePost;

    public Thread(Thread thread) {
        this.user = thread.getUser();
        this.title = thread.getTitle();
        this.content = thread.getContent();
        this.voteScore = thread.getVoteScore();
        this.datePost = thread.getDatePost();
    }

    public Thread(User user, String title, String content, int voteScore, LocalDate date_post) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.voteScore = voteScore;
        this.datePost = date_post;
    }

    public Thread() {

    }
}
