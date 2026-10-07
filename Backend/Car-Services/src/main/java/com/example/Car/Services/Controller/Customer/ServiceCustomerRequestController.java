
package com.example.Car.Services.Controller.Customer;

import com.example.Car.Services.DTO.request.ServiceCustomerRequestRequest;
import com.example.Car.Services.DTO.response.ServiceCustomerRequestResponse;
import com.example.Car.Services.service.ServiceCustomerRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-request")
public class ServiceCustomerRequestController {

    private final ServiceCustomerRequestService service;

    public ServiceCustomerRequestController(
            ServiceCustomerRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServiceCustomerRequestResponse> create(
            @RequestBody ServiceCustomerRequestRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ServiceCustomerRequestResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCustomerRequestResponse> getById(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCustomerRequestResponse> update(
            @PathVariable("id") Long id,
            @RequestBody ServiceCustomerRequestRequest request) {
        return ResponseEntity.ok(service.Update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}