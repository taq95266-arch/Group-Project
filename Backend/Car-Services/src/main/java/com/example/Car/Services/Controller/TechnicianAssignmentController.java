package com.example.Car.Services.Controller;


import com.example.Car.Services.DTO.TechnicianAssignmentDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.Interface.TechnicianAssignmentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TechnicianAssignmentController {


    private TechnicianAssignmentServiceInterface technicianAssignmentService;


    @PostMapping("/assignments/{assignmentId}/location")
    public ResponseEntity<MessageResponse> updateLocation(@PathVariable Long assignmentId,@RequestBody TechnicianAssignmentDTO dto){
        return ResponseEntity.ok(technicianAssignmentService.updateLocation(assignmentId,dto));
    }

}
