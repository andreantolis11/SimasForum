package com.simasforum.SimasForum.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Data
@Entity
@Table(name="reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long threadid;
    private long replyid;
    private long userid;
    @Column(nullable = false)
    private int level;
    private String replyname;
    @Column(nullable = false)
    private String content;
    private int upvote;
    private int downvote;

    public Reply(long id, long threadid, long replyid, long userid, int level, String replyname, String content, int upvote, int downvote) {
        this.id = id;
        this.threadid = threadid;
        this.replyid = replyid;
        this.userid = userid;
        this.level = level;
        this.replyname = replyname;
        this.content = content;
        this.upvote = upvote;
        this.downvote = downvote;
    }

    public Reply(Reply reply){
        this.threadid = reply.getThreadid();
        this.replyid = reply.getReplyid();
        this.userid = reply.getUserid();
        this.level = reply.getLevel();
        this.replyname = reply.getReplyname();
        this.content = reply.getContent();
        this.upvote = reply.getDownvote();
        this.downvote = reply.getDownvote();
    }

    public Reply(){

    }
}
