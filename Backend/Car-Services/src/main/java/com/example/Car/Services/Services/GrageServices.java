
package com.example.Car.Services.Services;

import com.example.Car.Services.DTO.GrageRequest;
import com.example.Car.Services.DTO.GrageResponse;
import com.example.Car.Services.Repository.GrageRepository;
import com.example.Car.Services.entities.Grage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrageServices {

    GrageRepository grageRepository;

    @Autowired
    public GrageServices(GrageRepository grageRepository) {
        this.grageRepository = grageRepository;
    }

    public Long addGrage(GrageRequest request) {

        Grage grage = new Grage();

        grage.setName(request.getName());
        grage.setLocation(request.getLocation());
        grage.setPhoneNumber(request.getPhoneNumber());
        grage.setCommerical_Registration(
                request.getCommerical_Registration()
        );

        return grageRepository.save(grage).getOwnerID();
    }

    public List<GrageResponse> getAllGrages() {

        List<Grage> grages = grageRepository.findAll();

        return GrageResponse.convertToResponse(grages);
    }

    public GrageResponse getById(Long id) {

        Grage grage = grageRepository.findById(id).orElse(null);

        if (grage == null) {
            return new GrageResponse();
        }

        return GrageResponse.convertToResponse(grage);
    }

    public GrageResponse updateGrage(Long id, GrageRequest request) {

        Grage grage = grageRepository.findById(id).orElse(null);

        if (grage == null) {
            return new GrageResponse();
        }

        grage.setName(request.getName());
        grage.setLocation(request.getLocation());
        grage.setPhoneNumber(request.getPhoneNumber());
        grage.setCommerical_Registration(
                request.getCommerical_Registration()
        );

        Grage updatedGrage = grageRepository.save(grage);

        return GrageResponse.convertToResponse(updatedGrage);
    }

    public Boolean deleteById(Long id) {

        Grage grage = grageRepository.findById(id).orElse(null);

        if (grage == null) {
            return false;
        }

        grageRepository.deleteById(id);

        return true;
    }
}

