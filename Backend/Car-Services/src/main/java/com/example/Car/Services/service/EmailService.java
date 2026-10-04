package com.example.Car.Services.service;

import com.example.Car.Services.Interface.EmailServiceInterface;
import com.example.Car.Services.expection.EmailNotVerifiedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmailService implements EmailServiceInterface {

    private static final Logger logger =
            LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url:http://localhost:4200}")
    private String frontendUrl;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Override
    public void sendVerificationEmail(
            String toEmail,
            String token
    ) {
        try {
            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(
                    "Car Service - Verify Your Email"
            );

            String verificationLink =
                    frontendUrl
                            + "/verify-email?token="
                            + token;

            String emailBody =
                    "Welcome to Car Services!\n\n"
                            + "Thank you for registering with us.\n\n"
                            + "Your account has been successfully created.\n\n"
                            + "Please verify your email address by clicking the link below:\n"
                            + verificationLink
                            + "\n\n"
                            + "Best regards,\n"
                            + "Car Services Team";

            message.setText(emailBody);
            mailSender.send(message);

            logger.info(
                    "Verification email sent to {}",
                    toEmail
            );

        } catch (Exception ex) {

            logger.error(
                    "Failed to send verification email to {} : {}",
                    toEmail,
                    ex.getMessage(),
                    ex
            );

            throw new EmailNotVerifiedException(
                    "Failed to send verification Email"
            );
        }
    }

    @Override
    public void sendPasswordRestEmail(
            String toEmail,
            String token
    ) {
        try {
            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(
                    "Car Service app - Password Reset"
            );

            String resetLink =
                    frontendUrl
                            + "/reset-password?token="
                            + token;

            String emailBody =
                    "Hello,\n\n"
                            + "We received a request to reset your password.\n\n"
                            + "Please click the link below:\n"
                            + resetLink
                            + "\n\n"
                            + "If you did not request this, please ignore this email.\n\n"
                            + "Best regards,\n"
                            + "Car Service Team";

            message.setText(emailBody);
            mailSender.send(message);

            logger.info(
                    "Password reset email sent to {}",
                    toEmail
            );

        } catch (Exception ex) {

            logger.error(
                    "Failed to send password reset email to {}: {}",
                    toEmail,
                    ex.getMessage(),
                    ex
            );

            throw new RuntimeException(
                    "Failed to send password reset email"
            );
        }
    }

    @Override
    public void sendSalaryUpdateEmail(
            String toEmail,
            BigDecimal newSalary
    ) {
        try {
            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(
                    "Car Services - Salary Update"
            );

            String emailBody =
                    "Hello,\n\n"
                            + "Your salary has been updated.\n\n"
                            + "New Salary: "
                            + newSalary
                            + " OMR\n\n"
                            + "If you have any questions, please contact your garage owner.\n\n"
                            + "Best regards,\n"
                            + "Car Services Team";

            message.setText(emailBody);
            mailSender.send(message);

            logger.info(
                    "Salary update email sent to {}",
                    toEmail
            );

        } catch (Exception ex) {

            logger.error(
                    "Failed to send salary update email to {}: {}",
                    toEmail,
                    ex.getMessage(),
                    ex
            );

            throw new RuntimeException(
                    "Failed to send salary update email"
            );
        }
    }

    @Override
    public void sendTechnicianAccountStatusEmail(
            String toEmail,
            boolean active
    ) {
        try {
            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(toEmail);

            if (active) {

                message.setSubject(
                        "Car Services - Account Activated"
                );

                message.setText(
                        "Hello,\n\n"
                                + "Your technician account has been activated.\n\n"
                                + "You can now access your account.\n\n"
                                + "Best regards,\n"
                                + "Car Services Team"
                );

            } else {

                message.setSubject(
                        "Car Services - Account Deactivated"
                );

                message.setText(
                        "Hello,\n\n"
                                + "Your technician account has been deactivated.\n\n"
                                + "Please contact your garage owner for more information.\n\n"
                                + "Best regards,\n"
                                + "Car Services Team"
                );
            }

            mailSender.send(message);

            logger.info(
                    "Account status email sent to {}",
                    toEmail
            );

        } catch (Exception ex) {

            logger.error(
                    "Failed to send account status email to {}: {}",
                    toEmail,
                    ex.getMessage(),
                    ex
            );

            throw new RuntimeException(
                    "Failed to send account status email"
            );
        }
    }

    @Override
    public void sendTechnicianNotificationEmail(
            String toEmail,
            String subject,
            String notificationMessage
    ) {
        try {
            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);

            String emailBody =
                    "Hello,\n\n"
                            + notificationMessage
                            + "\n\n"
                            + "Best regards,\n"
                            + "Car Services Team";

            message.setText(emailBody);

            mailSender.send(message);

            logger.info(
                    "Technician notification email sent to {}",
                    toEmail
            );

        } catch (Exception ex) {

            logger.error(
                    "Failed to send technician notification email to {}: {}",
                    toEmail,
                    ex.getMessage(),
                    ex
            );

            throw new RuntimeException(
                    "Failed to send technician notification email"
            );
        }
    }
}