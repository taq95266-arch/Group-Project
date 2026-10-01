package com.example.Car.Services.Interface;

import com.example.Car.Services.DTO.request.GrageRequest;
import com.example.Car.Services.DTO.response.MessageResponse;

public interface RequestGarageFormInterface {


    MessageResponse requestDocumation(GrageRequest request);
}
