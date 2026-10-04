package com.example.Car.Services.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "technicians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technician_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id", nullable = false)
    private RegistrationDocument garage;

    @Column(name = "specialization", length = 100)
    private String specialization;

    @Column(name = "is_available")
    private Boolean isAvailable = true;
}