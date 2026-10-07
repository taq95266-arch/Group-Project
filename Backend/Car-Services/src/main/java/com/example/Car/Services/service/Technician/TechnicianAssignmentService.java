package com.example.Car.Services.service.Technician;

import com.example.Car.Services.DTO.TechnicianAssignmentDTO;
import com.example.Car.Services.DTO.request.AssignTechnicianRequestDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.Interface.Technician.TechnicianAssignmentServiceInterface;
import com.example.Car.Services.Repository.GarageTechnicianRepository;
import com.example.Car.Services.Repository.ServiceCustomerRequestRepository;
import com.example.Car.Services.Repository.TechnicianAssignmentRepository;
import com.example.Car.Services.Repository.UserRepository;
import com.example.Car.Services.constant.GeoConstants;
import com.example.Car.Services.entities.Garage;
import com.example.Car.Services.entities.ServiceCustomerRequest;
import com.example.Car.Services.entities.Technician;
import com.example.Car.Services.entities.TechnicianAssignment;
import com.example.Car.Services.entities.User;
import com.example.Car.Services.enums.AssignmentStatus;
import com.example.Car.Services.expection.BadRequestException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TechnicianAssignmentService
        implements TechnicianAssignmentServiceInterface {

    private final SimpMessagingTemplate messagingTemplate;
    private final TechnicianAssignmentRepository technicianAssignmentRepository;
    private final ServiceCustomerRequestRepository serviceCustomerRequestRepository;
    private final GarageTechnicianRepository garageTechnicianRepository;
    private final UserRepository userRepository;

    public TechnicianAssignmentService(
            SimpMessagingTemplate messagingTemplate,
            TechnicianAssignmentRepository technicianAssignmentRepository,
            ServiceCustomerRequestRepository serviceCustomerRequestRepository,
            GarageTechnicianRepository garageTechnicianRepository,
            UserRepository userRepository) {

        this.messagingTemplate = messagingTemplate;
        this.technicianAssignmentRepository = technicianAssignmentRepository;
        this.serviceCustomerRequestRepository = serviceCustomerRequestRepository;
        this.garageTechnicianRepository = garageTechnicianRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public MessageResponse assignTechnician(
            AssignTechnicianRequestDTO dto,
            Long assignedByUserId) {

        if (dto.getRequestId() == null) {
            throw new BadRequestException("Service request ID is required");
        }

        if (dto.getTechnicianId() == null) {
            throw new BadRequestException("Technician ID is required");
        }

        ServiceCustomerRequest serviceRequest =
                serviceCustomerRequestRepository.findById(dto.getRequestId())
                        .orElseThrow(() ->
                                new BadRequestException("Service request not found"));

        Garage garage =
                serviceRequest
                        .getGarageServiceOption()
                        .getGarage();

        if (!garage.getOwnerId().equals(assignedByUserId)) {
            throw new BadRequestException(
                    "You are not allowed to assign a technician to this request"
            );
        }

        Technician technician =
                garageTechnicianRepository
                        .findByIdAndGarageId(
                                dto.getTechnicianId(),
                                garage.getId()
                        )
                        .orElseThrow(() ->
                                new BadRequestException(
                                        "Technician not found in this garage"
                                ));

        if (!Boolean.TRUE.equals(technician.getIsAvailable())) {
            throw new BadRequestException(
                    "Technician is not available"
            );
        }

        User assignedBy =
                userRepository.findById(assignedByUserId)
                        .orElseThrow(() ->
                                new BadRequestException("Garage owner not found"));

        TechnicianAssignment assignment =
                new TechnicianAssignment();

        assignment.setServiceRequest(serviceRequest);
        assignment.setTechnician(technician);
        assignment.setAssignedBy(assignedBy);
        assignment.setStatus(AssignmentStatus.ASSIGNED);
        assignment.setAssignedAt(Instant.now());

        technicianAssignmentRepository.save(assignment);

        return new MessageResponse(
                "Technician assigned successfully"
        );
    }

    @Override
    @Transactional
    public MessageResponse updateLocation(
            Long assignmentId,
            TechnicianAssignmentDTO dto) {

        if (assignmentId == null) {
            throw new BadRequestException(
                    "Assignment ID is required"
            );
        }

        if (dto.getLatitude() == null ||
                dto.getLongitude() == null) {

            throw new BadRequestException(
                    "Latitude and Longitude are required"
            );
        }

        TechnicianAssignment technicianAssignment =
                technicianAssignmentRepository
                        .findById(assignmentId)
                        .orElseThrow(() ->
                                new BadRequestException(
                                        "Technician assignment not found"
                                ));

        if (technicianAssignment.getStatus()
                == AssignmentStatus.CANCELLED ||
                technicianAssignment.getStatus()
                        == AssignmentStatus.COMPLETED) {

            throw new BadRequestException(
                    "Cannot update location for completed or cancelled assignment"
            );
        }

        if (dto.getLatitude()
                        .compareTo(GeoConstants.MIN_LATITUDE) < 0 ||
                dto.getLatitude()
                        .compareTo(GeoConstants.MAX_LATITUDE) > 0 ||
                dto.getLongitude()
                        .compareTo(GeoConstants.MIN_LONGITUDE) < 0 ||
                dto.getLongitude()
                        .compareTo(GeoConstants.MAX_LONGITUDE) > 0) {

            throw new BadRequestException(
                    "Invalid latitude or longitude values"
            );
        }

        technicianAssignment
                .setCurrentLatitude(dto.getLatitude());

        technicianAssignment
                .setCurrentLongitude(dto.getLongitude());

        technicianAssignment
                .setLastLocationUpdate(Instant.now());

        if (technicianAssignment.getStatus()
                == AssignmentStatus.PREPARING) {

            technicianAssignment
                    .setStatus(AssignmentStatus.ON_THE_WAY);
        }

        technicianAssignmentRepository
                .save(technicianAssignment);

        messagingTemplate.convertAndSend(
                "/topic/location/" + assignmentId,
                dto
        );

        return new MessageResponse(
                "Location updated successfully"
        );
    }
}