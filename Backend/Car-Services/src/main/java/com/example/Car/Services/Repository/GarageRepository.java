package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepository extends JpaRepository<Garage, Long> {
}
