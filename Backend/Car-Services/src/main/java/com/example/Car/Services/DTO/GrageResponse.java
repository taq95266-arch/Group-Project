package com.example.Car.Services.DTO;

import com.example.Car.Services.entities.Grage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class GrageResponse {

    private Long ownerID;
    private String name;
    private String location;
    private String phoneNumber;
    private String Commerical_Registration;

    public GrageResponse(Long ownerID,
                         String name,
                         String location,
                         String phoneNumber,
                         String Commerical_Registration) {

        this.ownerID = ownerID;
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.Commerical_Registration = Commerical_Registration;
    }

    public static GrageResponse convertToResponse(Grage grage) {

        return GrageResponse.builder()
                .ownerID(grage.getOwnerID())
                .name(grage.getName())
                .location(grage.getLocation())
                .phoneNumber(grage.getPhoneNumber())
                .Commerical_Registration(grage.getCommerical_Registration())
                .build();
    }

    public static List<GrageResponse> convertToResponse(List<Grage> grages) {

        return grages.stream()
                .map(GrageResponse::convertToResponse)
                .toList();
    }
}

