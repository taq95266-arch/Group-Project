package com.example.Car.Services.Interface.Technician;

import com.example.Car.Services.DTO.TechnicianAssignmentDTO;
import com.example.Car.Services.DTO.request.AssignTechnicianRequestDTO;
import com.example.Car.Services.DTO.response.MessageResponse;

public interface TechnicianAssignmentServiceInterface {

    MessageResponse assignTechnician(
            AssignTechnicianRequestDTO dto,
            Long assignedByUserId
    );

    MessageResponse updateLocation(
            Long assignmentId,
            TechnicianAssignmentDTO dto
    );
}