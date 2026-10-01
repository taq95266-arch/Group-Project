package com.example.Car.Services.expection;

public class PhoneAlreadyExistsException extends RuntimeException {

    public PhoneAlreadyExistsException(String message) {
        super(message);
    }
}
