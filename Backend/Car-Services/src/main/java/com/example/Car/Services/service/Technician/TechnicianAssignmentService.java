package com.example.Car.Services.service.Technician;


import com.example.Car.Services.DTO.TechnicianAssignmentDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.Interface.Technician.TechnicianAssignmentServiceInterface;
import com.example.Car.Services.Repository.TechnicianAssignmentRepository;
import com.example.Car.Services.constant.GeoConstants;
import com.example.Car.Services.entities.TechnicianAssignment;
import com.example.Car.Services.enums.AssignmentStatus;
import com.example.Car.Services.expection.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TechnicianAssignmentService  implements TechnicianAssignmentServiceInterface {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private TechnicianAssignmentRepository technicianAssignmentRepository;








    @Override
    @Transactional
    public MessageResponse updateLocation(Long assignmentId,TechnicianAssignmentDTO dto) {

        if(assignmentId == null) {
            throw new BadRequestException("Assignment ID is required");
        }
        if (dto.getLatitude() == null || dto.getLongitude() == null) {
            throw new BadRequestException(
                    "Latitude and Longitude are required!"
            );
        }
        TechnicianAssignment technicianAssignment = technicianAssignmentRepository.findById(assignmentId)
                                                  .orElseThrow(() -> new BadRequestException("User Assignment not found"));

        if(technicianAssignment.getStatus() == AssignmentStatus.CANCELLED ||
           technicianAssignment.getStatus() == AssignmentStatus.COMPLETED) {
            throw  new BadRequestException("Cannot update location for completed or cancelled assignment");
        }

        if(dto.getLongitude().compareTo(GeoConstants.MIN_LATITUDE) < 0 ||
           dto.getLatitude().compareTo(GeoConstants.MAX_LATITUDE) > 0 ||
           dto.getLongitude().compareTo(GeoConstants.MIN_LONGITUDE) < 0 ||
           dto.getLongitude().compareTo(GeoConstants.MAX_LONGITUDE) > 0 )
        {
            throw new IllegalArgumentException("Invalid latitude or longitude values");
        }

        technicianAssignment.setCurrentLongitude(dto.getLongitude());
        technicianAssignment.setCurrentLatitude(dto.getLatitude());
        technicianAssignment.setLastLocationUpdate(Instant.now());

          if(technicianAssignment.getStatus() == AssignmentStatus.PREPARING){
              technicianAssignment.setStatus(AssignmentStatus.ON_THE_WAY);
          }

        technicianAssignmentRepository.save(technicianAssignment);

        messagingTemplate.convertAndSend(
                "/topic/location/" + dto.getAssignmentId(),
                dto
        );

        return new MessageResponse("Location updated successfully");
    }
}
