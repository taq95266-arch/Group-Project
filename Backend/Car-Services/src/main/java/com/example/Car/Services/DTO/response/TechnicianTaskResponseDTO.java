package com.example.Car.Services.DTO.response;

import com.example.Car.Services.enums.AssignmentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TechnicianTaskResponseDTO {

    private Long assignmentId;
    private Long requestId;

    private String guestName;
    private String guestPhone;

    private String carMakeModel;
    private String carPlateNumber;

    private AssignmentStatus status;

    private BigDecimal currentLatitude;
    private BigDecimal currentLongitude;

    public TechnicianTaskResponseDTO(
            Long assignmentId,
            Long requestId,
            String guestName,
            String guestPhone,
            String carMakeModel,
            String carPlateNumber,
            AssignmentStatus status,
            BigDecimal currentLatitude,
            BigDecimal currentLongitude) {

        this.assignmentId = assignmentId;
        this.requestId = requestId;
        this.guestName = guestName;
        this.guestPhone = guestPhone;
        this.carMakeModel = carMakeModel;
        this.carPlateNumber = carPlateNumber;
        this.status = status;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
    }
}