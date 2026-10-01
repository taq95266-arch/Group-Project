package com.example.Car.Services.Interface;

public interface EmailServiceInterface {


    void sendVerificationEmail(String toEmail, String token);

    void sendPasswordRestEmail(String toEmail, String token );

    void sendWelcomeEmail(String toEmail, String fullName );


}
