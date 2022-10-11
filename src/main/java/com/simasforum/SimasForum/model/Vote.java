package com.simasforum.SimasForum.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "simas_user")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "threadid")
    private Long threadId;

    @Column(name = "replyid")
    private Long replyid;

    @Column(name = "userid")
    private Long userId;

    public Vote(Long threadId, Long replyid, Long userId) {
        this.threadId = threadId;
        this.replyid = replyid;
        this.userId = userId;
    }
}