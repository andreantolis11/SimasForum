package com.simasforum.SimasForum.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "simas_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    
    public User() {}
	public User(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

    public User(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
    }
}