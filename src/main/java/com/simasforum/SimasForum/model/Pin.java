package com.simasforum.SimasForum.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pin")
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long threadId;

    private Long replyId;

    private boolean isPin;

    private LocalDate date;

    public Pin() {

    }

    public Pin(Thread thread, boolean isPin, LocalDate date) {
        this.threadId = thread.getId();
        this.isPin = isPin;
        this.date = date;
    }

    public Pin(Reply reply, boolean isPin) {
        this.replyId = reply.getId();
        this.isPin = isPin;
    }

    public Pin(Thread thread, Reply reply, boolean isPin) {
        this.threadId = thread.getId();
        this.replyId = reply.getId();
        this.isPin = isPin;
    }
}
