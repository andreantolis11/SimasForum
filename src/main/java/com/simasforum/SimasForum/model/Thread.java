package com.simasforum.SimasForum.model;

import lombok.Data;

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

    @OneToMany(mappedBy = "thread",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Report> report;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private int voteScore;
    @Column(nullable = false)
    private LocalDate datePost;

    @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vote> votes;

    @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pin> pins;

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
