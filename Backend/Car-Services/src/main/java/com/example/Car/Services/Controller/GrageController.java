package com.example.Car.Services.Controller;

import com.example.Car.Services.DTO.GrageResponse;
import com.example.Car.Services.service.GrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grages")
public class GrageController {

    private final GrageService grageService;

    @Autowired
    public GrageController(GrageService grageService) {
        this.grageService = grageService;
    }

    @PostMapping
    public Long addGrage(@RequestBody GrageRequest request) {
        return grageService.addGrage(request);
    }

    @GetMapping
    public List<GrageResponse> getAllGrages() {
        return grageService.getAllGrages();
    }

    @GetMapping("/{id}")
    public GrageResponse getById(@PathVariable Long id) {
        return grageService.getById(id);
    }

    @PutMapping("/{id}")
    public GrageResponse updateGrage(
            @PathVariable Long id,
            @RequestBody GrageRequest request) {

        return grageService.updateGrage(id, request);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable Long id) {
        return grageService.deleteById(id);
    }
}















