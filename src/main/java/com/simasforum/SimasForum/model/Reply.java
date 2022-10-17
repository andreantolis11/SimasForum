package com.simasforum.SimasForum.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String replyName;

    @Column(nullable = false)
    private String content;

    private int voteScore;
    private Long threadId;

    private Long replyId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "replyId")
    private List<Reply> replies;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "replyId")
    private List<Report> reports;

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vote> votes;
    @ManyToOne
    private User user;

    public Reply() {
    }

    public Reply(String replyName, String content, User user, Thread parent) {
        this.replyName = replyName;
        this.content = content;
        this.user = user;
        this.threadId = parent.getId();
    }

    public Reply(String replyName, String content, User user, Reply parent) {
        this.replyName = replyName;
        this.content = content;
        this.user = user;
        this.replyId = parent.getId();
    }
}
