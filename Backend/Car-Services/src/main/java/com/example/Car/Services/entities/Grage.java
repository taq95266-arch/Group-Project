package com.example.Car.Services.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Grage{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long ownerID;
        private String name;
        private String location;
        private String phoneNumber;
        private String Commerical_Registration;
}
