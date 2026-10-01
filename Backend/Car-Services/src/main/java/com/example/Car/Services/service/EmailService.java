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


@Service
public class EmailService implements EmailServiceInterface {


    private static  final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;



    @Value("${app.frontend.url:http://localhost:4200}")
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
            throw  new EmailNotVerifiedException("Failed to send verification Email");
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

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Car Service -  Welcome");

            String emailBody =
                    "Welcome to Car Services! 🚗\n\n"
                            + "Thank you for registering with us.\n\n"
                            + "We are happy to have you with us!\n"
                    + "Welcome to Car Services! \uD83D\uDE97✨\n" +
                            "\n" +
                            "We’re delighted to have you with us. Your registration request has been successfully received and is currently under review.\n" +
                            "\n" +
                            "We will get back to you with an update as soon as possible.\n" +
                            "\n" +
                            "Thank you for choosing Car Services. We look forward to having you with us!\n"
                    + fullName + "";


            message.setText(emailBody);
            mailSender.send(message);
            logger.info("Welcome email sent to {} ", toEmail);

        }catch (Exception ex){
            logger.error("Failed to send Welcome email to {} : {}", toEmail,ex.getMessage(),ex);
            throw  new EmailNotVerifiedException("Failed to send Welcome Email");
        }
    }
}
