package com.simasforum.SimasForum.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "threadid")
    private Long threadId;

    @Column(name = "replyid", nullable = true)
    private Long replyId;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "isupvote")
    private boolean isUpVote;

    public Vote(){

    }
    public Vote(Long threadId, Long replyId, Long userId, boolean isUpVote) {
        this.threadId = threadId;
        this.replyId = replyId;
        this.userId = userId;
        this.isUpVote = isUpVote;
    }
}