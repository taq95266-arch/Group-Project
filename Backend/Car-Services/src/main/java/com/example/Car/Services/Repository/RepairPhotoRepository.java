package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.RepairPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairPhotoRepository extends JpaRepository<RepairPhoto, Long> {

    List<RepairPhoto> findByServiceRequest_RequestId(Long requestId);
}