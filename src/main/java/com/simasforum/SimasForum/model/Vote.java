package com.simasforum.SimasForum.model;

import javax.persistence.*;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Thread;
import lombok.Data;

@Data
@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Thread thread;
    @ManyToOne
    private Reply reply;
    @ManyToOne
    private User user;

    private boolean isUpVote;

    public Vote() {

    }

    public Vote(Thread thread, User user, boolean isUpVote) {
        this.thread = thread;
        this.user = user;
        this.isUpVote = isUpVote;
    }
    public Vote(Reply reply, User user, boolean isUpVote) {
        this.reply = reply;
        this.user = user;
        this.isUpVote = isUpVote;
    }
}