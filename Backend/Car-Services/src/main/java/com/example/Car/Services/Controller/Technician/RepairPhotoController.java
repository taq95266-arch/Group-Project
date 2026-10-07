package com.example.Car.Services.Controller.Technician;

import com.example.Car.Services.Interface.Technician.RepairPhotoServiceInterface;
import com.example.Car.Services.entities.RepairPhoto;
import com.example.Car.Services.entities.User;
import com.example.Car.Services.enums.PhotoType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/technician/requests")
@PreAuthorize("hasRole('TECHNICIAN')")
@RequiredArgsConstructor
public class RepairPhotoController {

    private final RepairPhotoServiceInterface repairPhotoService;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @PostMapping(
            value = "/{requestId}/photos",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PhotoResponse> uploadPhoto(
            @PathVariable Long requestId,
            @RequestParam PhotoType photoType,
            @RequestParam MultipartFile file,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUserId(currentUser);

        RepairPhoto photo = repairPhotoService.uploadPhoto(
                requestId, photoType, file, userId
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(photo));
    }

    @GetMapping("/{requestId}/photos")
    public List<PhotoResponse> getPhotos(
            @PathVariable Long requestId,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUserId(currentUser);

        return repairPhotoService.getPhotos(requestId, userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{requestId}/photos/{photoId}/image")
    public ResponseEntity<Resource> getImage(
            @PathVariable Long requestId,
            @PathVariable Long photoId,
            @AuthenticationPrincipal User currentUser
    ) throws IOException {
        Long userId = currentUserId(currentUser);

        RepairPhoto photo = repairPhotoService.getPhotos(requestId, userId)
                .stream()
                .filter(item -> item.getPhotoId().equals(photoId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Photo not found"
                ));

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path imagePath = uploadPath.resolve(photo.getImageUrl()).normalize();

        if (!imagePath.startsWith(uploadPath)
                || !Files.isRegularFile(imagePath)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Image file not found"
            );
        }

        String contentType = Files.probeContentType(imagePath);

        if (contentType == null || !List.of(
                "image/jpeg", "image/png", "image/webp"
        ).contains(contentType)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Image file not found"
            );
        }

        Resource resource = new UrlResource(imagePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    private Long currentUserId(User currentUser) {
        if (currentUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Login is required"
            );
        }

        return currentUser.getId();
    }

    private PhotoResponse toResponse(RepairPhoto photo) {
        Long requestId = photo.getServiceRequest().getRequestId();

        return new PhotoResponse(
                photo.getPhotoId(),
                requestId,
                photo.getPhotoType(),
                "/api/technician/requests/" + requestId
                        + "/photos/" + photo.getPhotoId() + "/image",
                photo.getCapturedAt()
        );
    }

    public record PhotoResponse(
            Long photoId,
            Long requestId,
            PhotoType photoType,
            String imageUrl,
            Instant capturedAt
    ) {
    }
}