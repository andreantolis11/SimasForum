package com.simasforum.SimasForum.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name ="pin")
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Thread thread;

    @ManyToOne
    private  Reply reply;

    private boolean isPin;

    private LocalDate date;

    public Pin(){

    }

    public Pin(Thread thread, boolean isPin, LocalDate date){
        this.thread = thread;
        this.isPin = isPin;
        this.date = date;
    }

    public Pin(Reply reply, boolean isPin){
        this.reply = reply;
        this.isPin = isPin;
    }
}
