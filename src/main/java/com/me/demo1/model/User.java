package com.me.demo1.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app_user")   // "user" ជា reserved word ក្នុង MySQL — ត្រូវជៀស
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;   // ត្រូវ encrypt (BCrypt) មិនរក្សា plain text
}
