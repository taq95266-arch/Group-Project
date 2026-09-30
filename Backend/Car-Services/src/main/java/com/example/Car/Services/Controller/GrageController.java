package com.example.Car.Services.Controller;

import com.example.Car.Services.DTO.GrageRequest;
import com.example.Car.Services.DTO.GrageResponse;
import com.example.Car.Services.Services.GrageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("grages")
public class GrageController {

    GrageServices grageServices;

    @Autowired
    public GrageController(GrageServices grageServices) {
        this.grageServices = grageServices;
    }

    @PostMapping
    public Long addGrage(@RequestBody GrageRequest request) {

        return grageServices.addGrage(request);
    }

    @GetMapping
    public List<GrageResponse> getAllGrages() {

        return grageServices.getAllGrages();
    }

    @GetMapping("{id}")
    public GrageResponse getById(@PathVariable Long id) {

        return grageServices.getById(id);
    }

    @PutMapping("{id}")
    public GrageResponse updateGrage(
            @PathVariable Long id,
            @RequestBody GrageRequest request) {

        return grageServices.updateGrage(id, request);
    }

    @DeleteMapping("{id}")
    public Boolean deleteGrage(@PathVariable Long id) {

        return grageServices.deleteById(id);
    }
}

