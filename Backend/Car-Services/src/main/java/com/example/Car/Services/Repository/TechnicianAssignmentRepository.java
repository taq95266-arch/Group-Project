package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.TechnicianAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicianAssignmentRepository extends JpaRepository<TechnicianAssignment, Long> {

    Optional<TechnicianAssignment>
    findFirstByServiceRequest_RequestIdAndTechnician_User_IdOrderByAssignedAtDesc(
            Long requestId,
            Long technicianUserId
    );
}