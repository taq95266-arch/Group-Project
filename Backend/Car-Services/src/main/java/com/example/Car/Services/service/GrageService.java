package com.example.Car.Services.service;

import com.example.Car.Services.DTO.GrageResponse;
import com.example.Car.Services.DTO.request.GrageRequest;
import com.example.Car.Services.Repository.GrageRepository;
import com.example.Car.Services.entities.Grage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrageService {

    private final GrageRepository grageRepository;

    @Autowired
    public GrageService(GrageRepository grageRepository) {
        this.grageRepository = grageRepository;
    }

    public Long addGrage(GrageRequest request) {

        Grage grage = new Grage();

        grage.setOwnerId(request.getOwnerId());
        grage.setGarageName(request.getGarageName());
        grage.setAddress(request.getAddress());
        grage.setLatitude(request.getLatitude());
        grage.setLongitude(request.getLongitude());
        grage.setPhone(request.getPhone());
        grage.setStatus(request.getStatus());

        return grageRepository.save(grage).getGarageId();
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

        grage.setOwnerId(request.getOwnerId());
        grage.setGarageName(request.getGarageName());
        grage.setAddress(request.getAddress());
        grage.setLatitude(request.getLatitude());
        grage.setLongitude(request.getLongitude());
        grage.setPhone(request.getPhone());
        grage.setStatus(request.getStatus());

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



