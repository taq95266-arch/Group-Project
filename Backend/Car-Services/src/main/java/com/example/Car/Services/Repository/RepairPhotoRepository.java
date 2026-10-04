package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.RepairPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairPhotoRepository extends JpaRepository<RepairPhoto, Long> {
}