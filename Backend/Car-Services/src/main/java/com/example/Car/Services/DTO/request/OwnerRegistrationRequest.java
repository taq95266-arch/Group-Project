package com.example.Car.Services.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OwnerRegistrationRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Commercial register number is required")
    private String commercialRegisterNumber;

    @NotNull(message = "Certificate file is required")
    private MultipartFile certificateFile;

    @NotBlank(message = "Governorate is required")
    private String governorate;

    @NotBlank(message = "State is required")
    private String state;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private String mapAddress;

}
