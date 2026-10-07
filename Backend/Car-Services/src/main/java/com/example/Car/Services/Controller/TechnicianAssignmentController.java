package com.example.Car.Services.Controller;

import com.example.Car.Services.DTO.TechnicianAssignmentDTO;
import com.example.Car.Services.DTO.request.AssignTechnicianRequestDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.Interface.Technician.TechnicianAssignmentServiceInterface;
import com.example.Car.Services.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TechnicianAssignmentController {

    private final TechnicianAssignmentServiceInterface technicianAssignmentService;

    @PostMapping("/owner/assignments")
    public ResponseEntity<MessageResponse> assignTechnician(
            @AuthenticationPrincipal User currentUser,
            @RequestBody AssignTechnicianRequestDTO dto) {

        return ResponseEntity.ok(
                technicianAssignmentService.assignTechnician(
                        dto,
                        currentUser.getId()
                )
        );
    }

    @PostMapping("/assignments/{assignmentId}/location")
    public ResponseEntity<MessageResponse> updateLocation(
            @PathVariable Long assignmentId,
            @RequestBody TechnicianAssignmentDTO dto) {

        return ResponseEntity.ok(
                technicianAssignmentService.updateLocation(
                        assignmentId,
                        dto
                )
        );
    }
}