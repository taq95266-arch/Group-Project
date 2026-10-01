package com.example.Car.Services.entities;

import com.example.Car.Services.enums.GarageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "garage")
@Getter
@Setter
public class Grage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long garageId;
        private Long ownerId;
        private String garageName;
        private String address;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private String phone;
        private GarageStatus status;
}
