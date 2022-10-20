package com.simasforum.SimasForum.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "simas_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Thread> thread;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    public User() {
    }

    public User(String name, String email, String password, Role role) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}