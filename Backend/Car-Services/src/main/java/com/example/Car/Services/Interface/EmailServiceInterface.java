package com.example.Car.Services.Interface;

import java.math.BigDecimal;

public interface EmailServiceInterface {

    void sendVerificationEmail(
            String toEmail,
            String token
    );

    void sendPasswordRestEmail(
            String toEmail,
            String token
    );

    void sendSalaryUpdateEmail(
            String toEmail,
            BigDecimal newSalary
    );

    void sendTechnicianAccountStatusEmail(
            String toEmail,
            boolean active
    );

    void sendTechnicianNotificationEmail(
            String toEmail,
            String subject,
            String message
    );
}