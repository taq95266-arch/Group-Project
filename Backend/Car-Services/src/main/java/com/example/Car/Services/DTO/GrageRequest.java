
package com.example.Car.Services.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GrageRequest {

    private String name;
    private String location;
    private String phoneNumber;
    private String Commerical_Registration;

    public GrageRequest(String name,
                        String location,
                        String phoneNumber,
                        String Commerical_Registration) {

        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.Commerical_Registration = Commerical_Registration;
    }
}

