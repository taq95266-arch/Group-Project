package com.example.Car.Services.service;

import com.example.Car.Services.DTO.request.ServiceCustomerRequestRequest;
import com.example.Car.Services.DTO.response.ServiceCustomerRequestResponse;
import com.example.Car.Services.Repository.GarageServiceOptionRepository;
import com.example.Car.Services.Repository.ServiceCustomerRequestRepository;
import com.example.Car.Services.entities.GarageServiceOption;
import com.example.Car.Services.entities.ServiceCustomerRequest;
import com.example.Car.Services.enums.ServiceRequestStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceCustomerRequestService {

    private final ServiceCustomerRequestRepository serviceCustomerRequestRepository;
    private final GarageServiceOptionRepository garageServiceOptionRepository;

    public ServiceCustomerRequestService(
            ServiceCustomerRequestRepository serviceCustomerRequestRepository,
            GarageServiceOptionRepository garageServiceOptionRepository) {

        this.serviceCustomerRequestRepository = serviceCustomerRequestRepository;
        this.garageServiceOptionRepository = garageServiceOptionRepository;
    }

    public ServiceCustomerRequestResponse create(
            ServiceCustomerRequestRequest request) {

        GarageServiceOption garageOption =
                garageServiceOptionRepository.findById(request.getGarageOptionId())
                        .orElseThrow(() ->
                                new RuntimeException("Garage service option not found"));

        if (!Boolean.TRUE.equals(garageOption.getIsAvailable())) {
            throw new RuntimeException("This service is currently unavailable");
        }

        ServiceCustomerRequest serviceRequest =
                new ServiceCustomerRequest();

        serviceRequest.setGarageServiceOption(garageOption);
        serviceRequest.setGuestName(request.getGuestName());
        serviceRequest.setGuestPhone(request.getGuestPhone());
        serviceRequest.setCarMakeModel(request.getCarMakeModel());
        serviceRequest.setCarPlateNumber(request.getCarPlateNumber());

        serviceRequest.setAppliedPrice(garageOption.getPrice());

        serviceRequest.setStatus(ServiceRequestStatus.PENDING);

        serviceRequest.setCreatedAt(LocalDateTime.now());

        ServiceCustomerRequest saved =
                serviceCustomerRequestRepository.save(serviceRequest);

        return toResponse(saved);
    }

    public List<ServiceCustomerRequestResponse> getAll() {

        return serviceCustomerRequestRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ServiceCustomerRequestResponse getById(Long id) {

        ServiceCustomerRequest serviceRequest =
                serviceCustomerRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Service request not found"));

        return toResponse(serviceRequest);
    }

    public ServiceCustomerRequestResponse update(
            Long id,
            ServiceCustomerRequestRequest request) {

        ServiceCustomerRequest serviceRequest =
                serviceCustomerRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Service request not found"));

        GarageServiceOption garageOption =
                garageServiceOptionRepository.findById(request.getGarageOptionId())
                        .orElseThrow(() ->
                                new RuntimeException("Garage service option not found"));

        if (!Boolean.TRUE.equals(garageOption.getIsAvailable())) {
            throw new RuntimeException("This service is currently unavailable");
        }

        serviceRequest.setGarageServiceOption(garageOption);
        serviceRequest.setGuestName(request.getGuestName());
        serviceRequest.setGuestPhone(request.getGuestPhone());
        serviceRequest.setCarMakeModel(request.getCarMakeModel());
        serviceRequest.setCarPlateNumber(request.getCarPlateNumber());

        serviceRequest.setAppliedPrice(garageOption.getPrice());

        ServiceCustomerRequest updated =
                serviceCustomerRequestRepository.save(serviceRequest);

        return toResponse(updated);
    }

    public void delete(Long id) {

        if (!serviceCustomerRequestRepository.existsById(id)) {
            throw new RuntimeException("Service request not found");
        }

        serviceCustomerRequestRepository.deleteById(id);
    }

    private ServiceCustomerRequestResponse toResponse(
            ServiceCustomerRequest serviceRequest) {

        return new ServiceCustomerRequestResponse(
                serviceRequest.getRequestId(),
                serviceRequest.getGarageServiceOption().getGarageOptionId(),
                serviceRequest.getGuestName(),
                serviceRequest.getGuestPhone(),
                serviceRequest.getCarMakeModel(),
                serviceRequest.getCarPlateNumber(),
                serviceRequest.getAppliedPrice(),
                serviceRequest.getStatus(),
                serviceRequest.getCreatedAt()
        );
    }
}