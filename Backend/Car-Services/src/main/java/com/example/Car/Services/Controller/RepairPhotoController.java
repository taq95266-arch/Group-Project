package com.example.Car.Services.Controller;

import com.example.Car.Services.DTO.request.RepairPhotoCreateRequest;
import com.example.Car.Services.DTO.response.RepairPhotoResponse;
import com.example.Car.Services.service.RepairPhotoService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/service-requests/{requestId}/photos")
public class RepairPhotoController {

    private final RepairPhotoService repairPhotoService;

    public RepairPhotoController(
            RepairPhotoService repairPhotoService
    ) {
        this.repairPhotoService = repairPhotoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepairPhotoResponse addPhoto(
            @PathVariable Long requestId,
            @RequestBody RepairPhotoCreateRequest request
    ) {
        return repairPhotoService.addPhoto(requestId, request);
    }

    @GetMapping
    public List<RepairPhotoResponse> getAllPhotos() {
        return repairPhotoService.getAllPhotos();
    }

    @GetMapping("/{photoId}")
    public RepairPhotoResponse getPhoto(
            @PathVariable Long photoId
    ) {
        return repairPhotoService.getPhoto(photoId);
    }

    @DeleteMapping("/{photoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(
            @PathVariable Long photoId
    ) {
        repairPhotoService.deletePhoto(photoId);
    }
}