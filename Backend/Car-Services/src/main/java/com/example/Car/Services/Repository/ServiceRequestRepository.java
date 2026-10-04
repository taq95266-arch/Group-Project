package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}