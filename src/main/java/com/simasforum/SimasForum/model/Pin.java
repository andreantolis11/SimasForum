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

    @OneToOne
    @JoinColumn(name = "replyId", referencedColumnName = "id")
    private Reply reply;

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
        this.reply = reply;
        this.isPin = isPin;
    }
}
