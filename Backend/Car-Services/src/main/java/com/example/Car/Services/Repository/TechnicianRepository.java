package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    List<Technician> findByGarageDocId(Long garageDocId);

    Optional<Technician> findByUserId(Long userId);
}