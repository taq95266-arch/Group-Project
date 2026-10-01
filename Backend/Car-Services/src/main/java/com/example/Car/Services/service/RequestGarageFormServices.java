package com.example.Car.Services.service;


import com.example.Car.Services.DTO.request.GrageRequest;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.Interface.RequestGarageFormInterface;
import com.example.Car.Services.Repository.UserRepository;
import com.example.Car.Services.Utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RequestGarageFormServices implements RequestGarageFormInterface {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ServiceUtils serviceUtils;
    @Autowired
    private EmailService emailService;

    @Override
    public MessageResponse requestDocumation(GrageRequest request) {

        emailService.sendVerificationEmail(userRequest.getEmail(),verificationToken);
        return null;
    }

}
