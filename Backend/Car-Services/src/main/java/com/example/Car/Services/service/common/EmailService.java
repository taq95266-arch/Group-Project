package com.example.Car.Services.service.common;

import com.example.Car.Services.Interface.Commn.EmailServiceInterface;
import com.example.Car.Services.expection.EmailSendingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService implements EmailServiceInterface {


    private static  final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url")
    private String frontendUrl;

    @Value("${app.mail.from}")
    private String fromEmail;


    @Override
    public void sendVerificationEmail(String toEmail, String token) {

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Car Service -  Verify Your Email");

            String verificationLink = frontendUrl + "/verify-email?token=" + token;
            String emailBody =
                    "Welcome to Car Services! 🚗\n\n"
                            + "Thank you for registering with us.\n\n"
                            + "We are happy to have you with us!\n"
                            + "Your account has been successfully created.\n\n"
                            + "Please verify your email address by clicking the link below:\n"
                            + verificationLink + "\n\n"
                            + "Once your email is verified, you can start using your account.\n\n"
                            + "If you have any questions or need assistance, please feel free to contact us.\n\n"
                            + "Best regards,\n"
                            + "Car Services Team";


               message.setText(emailBody);
               mailSender.send(message);
               logger.info("Verification email sent to {} ", toEmail);

        }catch (Exception ex){
            logger.error("Failed to send verification email to {} : {}", toEmail,ex.getMessage(),ex);
            throw  new EmailSendingException("Failed to send verification Email");
        }
    }

    @Override
    public void sendPasswordRestEmail(String toEmail, String token) {
          try{
             SimpleMailMessage message = new SimpleMailMessage();
             message.setFrom(fromEmail);
             message.setTo(toEmail);
             message.setSubject("Car Service app - Password Reset");
             String resetLink = frontendUrl + "/reset-password?token=" + token;

              String emailBody = "" + "Hello,\n\n" +
                      "We received a request to reset your password for your Car Service account.\n\n"
                      + "Please click the link below to reset your password:\n" +
                      resetLink + "\n\n" + "If you did not request a password reset, please ignore this email.\n\n"
                      + "For security reasons, please do not share this link with anyone.\n\n"
                      + "Best regards,\n"
                      + "Car Service Team";

              message.setText(emailBody);
              mailSender.send(message);
              logger.info("Password reset email sent to: {}", toEmail);

          }catch (Exception ex) {
              logger.error("Failed to send password reset email to {}: {}", toEmail, ex.getMessage(),ex);
              throw  new RuntimeException("Failed to send password reset email");
          }
    }

    @Override
    public void sendWelcomeEmail(String toEmail, String fullName) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Car Services - Application Received");

            String emailBody =
                    "Dear " + fullName + ",\n\n"
                            + "Welcome to Car Services! 🚗\n\n"
                            + "Thank you for registering your garage with us.\n\n"
                            + "We have successfully received your registration request along with your commercial documents.\n\n"
                            + "Our admin team is currently reviewing your application. You will receive an update regarding your application approval status very soon.\n\n"
                            + "If you have any questions or need further assistance, please feel free to reach out to our support team.\n\n"
                            + "Best regards,\n"
                            + "Car Services Team";

            message.setText(emailBody);
            mailSender.send(message);
            logger.info("Welcome email sent successfully to {} ({})", toEmail, fullName);

        } catch (Exception ex) {
            logger.error("Failed to send welcome email to {}: {}", toEmail, ex.getMessage(), ex);
            throw new EmailSendingException("Failed to send welcome email");
        }
    }


    @Override
    public void sendApprovalEmail(String toEmail, String fullName, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Car Services - Application Approved 🎉");

            String verificationLink = frontendUrl + "/verify-email?token=" + token;

            String emailBody =
                    "Dear " + fullName + ",\n\n"
                            + "Great news! We are pleased to inform you that your garage registration request has been APPROVED. 🎉\n\n"
                            + "Your garage profile is now active on the Car Services platform. To complete your registration and log in to your dashboard, please activate your account using the link below:\n\n"
                            + verificationLink + "\n\n"
                            + "Once activated, you can log in, complete your profile setup, and start accepting service requests right away.\n\n"
                            + "Welcome aboard! We look forward to a successful partnership with you.\n\n"
                            + "If you need any assistance getting started, please feel free to reach out to our support team.\n\n"
                            + "Best regards,\n"
                            + "Car Services Team";

            message.setText(emailBody);
            mailSender.send(message);
            logger.info("Approval email sent successfully to {} ({})", toEmail, fullName);

        } catch (Exception ex) {
            logger.error("Failed to send approval email to {}: {}", toEmail, ex.getMessage(), ex);
            throw new EmailSendingException("Failed to send approval email");
        }
    }

    @Override
    public void sendRejectionEmail(String toEmail, String fullName, String reason) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Car Services - Application Status Update");

            String emailBody =
                    "Dear " + fullName + ",\n\n"
                            + "Thank you for your interest in joining Car Services.\n\n"
                            + "After reviewing your garage registration application and documents, we regret to inform you that your request could not be approved at this time.\n\n"
                            + "Reason for Rejection:\n"
                            + "\"" + (reason != null && !reason.trim().isEmpty() ? reason : "Documents do not meet our criteria.") + "\"\n\n"
                            + "If you believe this decision was made in error or if you wish to upload updated documents, please contact our support team or re-apply through our portal.\n\n"
                            + "Best regards,\n"
                            + "Car Services Team";

            message.setText(emailBody);
            mailSender.send(message);
            logger.info("Rejection email sent successfully to {} ({})", toEmail, fullName);

        } catch (Exception ex) {
            logger.error("Failed to send rejection email to {}: {}", toEmail, ex.getMessage(), ex);
            throw new EmailSendingException("Failed to send rejection email");
        }
    }


}
