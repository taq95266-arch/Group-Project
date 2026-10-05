package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.ServiceCustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCustomerRequestRepository extends JpaRepository<ServiceCustomerRequest,Long> {
}
