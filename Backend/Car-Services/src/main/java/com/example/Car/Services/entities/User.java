package com.example.Car.Services.entities;


import com.example.Car.Services.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

    @Column(nullable = false, unique = true, length = 12)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;


    @Column(nullable = true)
    private Boolean has_used_free_trial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private Role role = Role.GARAGE_OWNER;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(unique = true)
    private String  verificationToken;


    @Column
    private Instant verificationTokenExpiry;

    @Column
    private String passwordResetToken;

    @Column
    private Instant passwordResetTokenExpiry;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;










}
