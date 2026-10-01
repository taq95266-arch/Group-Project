package com.example.Car.Services.expection;

public class InvalidRoleException extends RuntimeException {

    public InvalidRoleException(String message) {
        super(message);
    }
}
