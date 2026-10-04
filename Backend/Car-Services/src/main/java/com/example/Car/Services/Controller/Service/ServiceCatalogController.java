package com.example.Car.Services.Controller.Service;

import com.example.Car.Services.DTO.request.ServiceOptionRequest;
import com.example.Car.Services.DTO.response.ServiceOptionResponse;
import com.example.Car.Services.DTO.request.ServiceRequest;
import com.example.Car.Services.DTO.response.ServiceResponse;
import com.example.Car.Services.service.ServiceCatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@PreAuthorize("hasRole('ADMIN')")
public class ServiceCatalogController {

    private final ServiceCatalogService catalogService;

    public ServiceCatalogController(ServiceCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse createService(
            @RequestBody ServiceRequest request
    ) {
        return catalogService.createService(request);
    }

    @GetMapping
    public List<ServiceResponse> getAllServices() {
        return catalogService.getAllServices();
    }

    @GetMapping("/{serviceId}")
    public ServiceResponse getService(
            @PathVariable Long serviceId
    ) {
        return catalogService.getService(serviceId);
    }

    @PutMapping("/{serviceId}")
    public ServiceResponse updateService(
            @PathVariable Long serviceId,
            @RequestBody ServiceRequest request
    ) {
        return catalogService.updateService(serviceId, request);
    }

    @DeleteMapping("/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long serviceId) {
        catalogService.deleteService(serviceId);
    }

    @PostMapping("/{serviceId}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOptionResponse createOption(
            @PathVariable Long serviceId,
            @RequestBody ServiceOptionRequest request
    ) {
        return catalogService.createOption(serviceId, request);
    }

    @GetMapping("/{serviceId}/options")
    public List<ServiceOptionResponse> getOptions(
            @PathVariable Long serviceId
    ) {
        return catalogService.getOptions(serviceId);
    }

    @GetMapping("/{serviceId}/options/{optionId}")
    public ServiceOptionResponse getOption(
            @PathVariable Long serviceId,
            @PathVariable Long optionId
    ) {
        return catalogService.getOption(serviceId, optionId);
    }

    @PutMapping("/{serviceId}/options/{optionId}")
    public ServiceOptionResponse updateOption(
            @PathVariable Long serviceId,
            @PathVariable Long optionId,
            @RequestBody ServiceOptionRequest request
    ) {
        return catalogService.updateOption(
                serviceId,
                optionId,
                request
        );
    }

    @DeleteMapping("/{serviceId}/options/{optionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(
            @PathVariable Long serviceId,
            @PathVariable Long optionId
    ) {
        catalogService.deleteOption(serviceId, optionId);
    }
}