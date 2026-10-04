package com.example.Car.Services.Controller.GarageOwner;


import com.example.Car.Services.DTO.request.TechnicianRequestDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.DTO.response.TechnicianResponseDTO;
import com.example.Car.Services.service.GarageOwner.TechnicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/owner")
@PreAuthorize("hasRole('GARAGE_OWNER')")
@RequiredArgsConstructor
public class GarageTechnicianController {

    private final TechnicianService technicianService;

    @PostMapping("/garages/{garageId}/technicians")
    public ResponseEntity<MessageResponse> addTechnician(@Valid @RequestBody TechnicianRequestDTO request,Long garageId) {
        MessageResponse response = technicianService.addTechnician(request, garageId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/garage/{garageId}")
    public ResponseEntity<List<TechnicianResponseDTO>> getTechniciansByGarage(@PathVariable Long garageId) {
        List<TechnicianResponseDTO> technicians = technicianService.getTechniciansByGarage(garageId);
        return ResponseEntity.ok(technicians);
    }
}