package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TechnicianRepository
        extends JpaRepository<Technician, Long> {

    List<Technician> findByGarageOwner_Email(String email);

    Optional<Technician> findByTechnicianIdAndGarageOwner_Email(
            Long technicianId,
            String email
    );

    boolean existsByUser_Id(Long userId);
}