package com.example.Car.Services.Repository;

import com.example.Car.Services.entities.GarageServiceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GarageServiceOptionRepository
        extends JpaRepository<GarageServiceOption, Long> {

    List<GarageServiceOption> findByGarageId(Long garageId);

    Optional<GarageServiceOption> findByGarageOptionIdAndGarageId(
            Long garageOptionId,
            Long garageId
    );

    boolean existsByGarageIdAndServiceOptionServiceOptionId(
            Long garageId,
            Long serviceOptionId
    );
}