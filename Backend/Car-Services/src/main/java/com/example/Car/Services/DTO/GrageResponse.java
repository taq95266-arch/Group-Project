package com.example.Car.Services.DTO;

import com.example.Car.Services.entities.Grage;
import com.example.Car.Services.enums.GarageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GrageResponse {

    private Long garageId;
    private Long ownerId;
    private String garageName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String phone;
    private GarageStatus status;

    public static GrageResponse convertToResponse(Grage grage) {

        return new GrageResponse(
                grage.getGarageId(),
                grage.getOwnerId(),
                grage.getGarageName(),
                grage.getAddress(),
                grage.getLatitude(),
                grage.getLongitude(),
                grage.getPhone(),
                grage.getStatus()
        );
    }

    public static List<GrageResponse> convertToResponse(List<Grage> grages) {

        return grages.stream()
                .map(GrageResponse::convertToResponse)
                .toList();
    }
}