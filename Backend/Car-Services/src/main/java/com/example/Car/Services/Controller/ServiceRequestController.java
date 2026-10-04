package com.example.Car.Services.Controller;

import com.example.Car.Services.DTO.request.ServiceRequestCreateRequest;
import com.example.Car.Services.DTO.request.ServiceRequestStatusRequest;
import com.example.Car.Services.DTO.response.ServiceRequestResponse;
import com.example.Car.Services.service.ServiceRequestService;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(
            ServiceRequestService serviceRequestService
    ) {
        this.serviceRequestService = serviceRequestService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceRequestResponse createRequest(
            @RequestBody ServiceRequestCreateRequest request
    ) {
        return serviceRequestService.createRequest(request);
    }

    @GetMapping
    public List<ServiceRequestResponse> getAllRequests() {
        return serviceRequestService.getAllRequests();
    }

    @GetMapping("/{requestId}")
    public ServiceRequestResponse getRequest(
            @PathVariable Long requestId
    ) {
        return serviceRequestService.getRequest(requestId);
    }

    @PutMapping("/{requestId}/status")
    public ServiceRequestResponse updateStatus(
            @PathVariable Long requestId,
            @RequestBody ServiceRequestStatusRequest request
    ) {
        return serviceRequestService.updateStatus(
                requestId,
                request.status()
        );
    }

    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequest(
            @PathVariable Long requestId
    ) {
        serviceRequestService.deleteRequest(requestId);
    }
}