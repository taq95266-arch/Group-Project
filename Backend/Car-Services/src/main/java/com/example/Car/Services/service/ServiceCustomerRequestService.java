package com.example.Car.Services.service;

import com.example.Car.Services.DTO.request.ServiceCustomerRequestRequest;
import com.example.Car.Services.DTO.request.ServiceRequest;
import com.example.Car.Services.DTO.response.ServiceCustomerRequestResponse;
import com.example.Car.Services.Repository.ServiceCustomerRequestRepository;
import com.example.Car.Services.entities.ServiceCustomerRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Service
public class ServiceCustomerRequestService {

    private final ServiceCustomerRequestRepository serviceCustomerRequestRepository;

    public ServiceCustomerRequestService(ServiceCustomerRequestRepository serviceCustomerRequestRepository) {
        this.serviceCustomerRequestRepository = serviceCustomerRequestRepository;

    }

    public ServiceCustomerRequestResponse create(ServiceCustomerRequestRequest request) {
        ServiceCustomerRequest serviceRequest = new ServiceCustomerRequest();
        serviceRequest.setGuest_Name(request.getGuest_Name());
        serviceRequest.setGuest_phone(request.getGuest_phone());
        serviceRequest.setCar_make_model(request.getCar_make_model());
        serviceRequest.setCar_plate_model(request.getCar_plate_model());
        serviceRequest.setApplied_price(request.getApplied_price());
        serviceRequest.setStatus(request.getStatus());
        serviceRequest.setCreated_at(LocalDateTime.now());

        ServiceCustomerRequest saved = serviceCustomerRequestRepository.save(serviceRequest);
        return new ServiceCustomerRequestResponse(
                saved.getRequest_id(),
        saved.getGuest_Name(),
        saved.getGuest_phone(),
        saved.getCar_make_model(),
        saved.getCar_plate_model(),
        saved.getApplied_price(),
        saved.getStatus(),
        saved.getCreated_at()
        );
    }

    public List<ServiceCustomerRequestResponse> getAll() {

        return serviceCustomerRequestRepository.findAll()
                .stream()
                .map(serviceRequest -> new ServiceCustomerRequestResponse(
                        serviceRequest.getRequest_id(),
                        serviceRequest.getGuest_Name(),
                        serviceRequest.getGuest_phone(),
                        serviceRequest.getCar_make_model(),
                        serviceRequest.getCar_plate_model(),
                        serviceRequest.getApplied_price(),
                        serviceRequest.getStatus(),
                        serviceRequest.getCreated_at()
                ))
                .toList();
    }

    public ServiceCustomerRequestResponse getById(Long id) {

        ServiceCustomerRequest serviceRequest =
                serviceCustomerRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Service request not found"));

        return new ServiceCustomerRequestResponse(
                serviceRequest.getRequest_id(),
                serviceRequest.getGuest_Name(),
                serviceRequest.getGuest_phone(),
                serviceRequest.getCar_make_model(),
                serviceRequest.getCar_plate_model(),
                serviceRequest.getApplied_price(),
                serviceRequest.getStatus(),
                serviceRequest.getCreated_at()
        );
    }
    public ServiceCustomerRequestResponse Update(
            Long id,
            ServiceCustomerRequestRequest request) {

        ServiceCustomerRequest serviceRequest =
                serviceCustomerRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Service request not found"));

        serviceRequest.setGuest_Name(request.getGuest_Name());
        serviceRequest.setGuest_phone(request.getGuest_phone());
        serviceRequest.setCar_make_model(request.getCar_make_model());
        serviceRequest.setCar_plate_model(request.getCar_plate_model());
        serviceRequest.setApplied_price(request.getApplied_price());
        serviceRequest.setStatus(request.getStatus());

        ServiceCustomerRequest updated =
                serviceCustomerRequestRepository.save(serviceRequest);

        return new ServiceCustomerRequestResponse(
                updated.getRequest_id(),
                updated.getGuest_Name(),
                updated.getGuest_phone(),
                updated.getCar_make_model(),
                updated.getCar_plate_model(),
                updated.getApplied_price(),
                updated.getStatus(),
                updated.getCreated_at()
        );
    }
    public void delete(Long id) {
        if (!serviceCustomerRequestRepository.existsById(id)) {
            throw new RuntimeException("Service request not found");
        }

        serviceCustomerRequestRepository.deleteById(id);
    }


}
