package com.example.Car.Services.service;

import com.example.Car.Services.Interface.AuthServiceInterface;
import com.example.Car.Services.DTO.request.UserRequest;
import com.example.Car.Services.DTO.response.EmailValidationResponse;
import com.example.Car.Services.DTO.response.LoginResponse;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.entities.User;
import com.example.Car.Services.enums.Role;
import com.example.Car.Services.expection.*;
import com.example.Car.Services.Repository.UserRepository;
import com.example.Car.Services.Security.JwtUtil;
import com.example.Car.Services.Utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


@Service
public class AuthService implements AuthServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ServiceUtils serviceUtils;


    @Override
    public MessageResponse signup(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())) {
             throw new EmailAlreadyExistsException("Email already exists");
        }

        if(userRepository.existsByPhone(userRequest.getPhone())){
            throw  new PhoneAlreadyExistsException("Phone already exists");
        }


        User user = new User();
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFullName(userRequest.getFullName());
        user.setRole(Role.USER);
        user.setActive(true);
        user.setEmailVerified(false);
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(Instant.now().plusSeconds(86400));
        userRepository.save(user);
        emailService.sendVerificationEmail(userRequest.getEmail(),verificationToken);
        return new MessageResponse("Registration successful! Please check your email to verify your account.");
    }

    @Override
    public LoginResponse login(String email, String password) {


        System.out.println("login register");
       User user = userRepository
               .findByEmail(email)
               .filter(u -> passwordEncoder.matches(password,u.getPassword()))
               .orElseThrow(() -> new BadCredentialException("Invalid Email or password"));

        if(!user.getActive()){
            throw new AccountDeactivatedException("your Account has been deactivate please contact support for assistance");
        }

        if(!user.isEmailVerified()){
            throw new EmailNotVerifiedException("Please verify your email adderss before loggin in. Check your inbox for the verification link");
        }

        System.out.println("USER ROLE = " + user.getRole());


        final String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponse(token,user.getEmail(),user.getFullName(),user.getRole().name());


    }

    @Override
    public EmailValidationResponse validateEmail(String email) {
        Boolean exists = userRepository.existsByEmail(email);
        return  new EmailValidationResponse(exists, !exists);
    }

    @Override
    public MessageResponse verifyEmail(String token) {
        User user =
                userRepository.findByVerificationToken(token)
                        .orElseThrow(() -> new InvalidTokenException("Invalid or expired verification token"));


        if(user.getVerificationTokenExpiry() == null || user.getVerificationTokenExpiry().isBefore(Instant.now())){
            throw new InvalidTokenException("Verification link expired,Please request new one");
        }


        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);
        userRepository.save(user);
        return new MessageResponse("Email verified successfully, you can login now");
    }




    @Override
    public MessageResponse resendVerification(String email) {
        User user =serviceUtils.getUserByEmailOrThrow(email);

        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(Instant.now().plusSeconds(84600));
        userRepository.save(user);
        emailService.sendVerificationEmail(email,verificationToken);

        return new MessageResponse("Verification email resent sucessfully Please check your inbox ");
    }

    @Override
    public MessageResponse forgotPassword(String email) {

        User user = serviceUtils.getUserByEmailOrThrow(email);
        String resetToken = UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenExpiry(Instant.now().plusSeconds(3600));

        userRepository.save(user);
        emailService.sendPasswordRestEmail(email,resetToken);

        return new MessageResponse("Password reset email sent successfully! Please check your inbox ");


    }

    @Override
    public MessageResponse resetPassword(String token, String newPassword) {

          User user = userRepository.findByPasswordResetToken(token)
                  .orElseThrow(() -> new InvalidTokenException("Invalid or expired token"));

         if(user.getPasswordResetTokenExpiry() == null || user.getPasswordResetTokenExpiry().isBefore(Instant.now())){
             throw new InvalidTokenException("Reset token has expired");
         }

         user.setPassword(passwordEncoder.encode(newPassword));
         user.setPasswordResetToken(null);
         user.setPasswordResetTokenExpiry(null);
         userRepository.save(user);
        return new MessageResponse("password reset successfully. You can now login with your password ");
    }

    @Override
    public MessageResponse changePassword(String email, String currentPassword, String newPassword) {

        User user = serviceUtils.getUserByEmailOrThrow(email);

        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw  new InvalidCredentialsException("Current password is incorrect");
        }

         user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new MessageResponse("Password change successfully");


    }

    @Override
    public LoginResponse currentUser(String email) {
        User user = serviceUtils.getUserByEmailOrThrow(email);
        return new LoginResponse(null,user.getEmail(), user.getFullName(), user.getRole().name());
    }
}
