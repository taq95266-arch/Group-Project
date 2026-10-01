package com.example.Car.Services.expection;

public class AccountDeactivatedException extends RuntimeException {

    public AccountDeactivatedException(String message) {
        super(message);
    }
}
