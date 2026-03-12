package com.ripple.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "users",
        uniqueConstraints ={
        @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
   private String password;
    @Column(nullable = false)
   private String username;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @PrePersist //Entity lifecycle hooks. Audit Attributes.
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
