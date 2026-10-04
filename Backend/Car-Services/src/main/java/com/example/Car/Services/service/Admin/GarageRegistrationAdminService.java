package com.example.Car.Services.service.Admin;

import com.example.Car.Services.DTO.request.DecisionRequestDTO;
import com.example.Car.Services.DTO.response.MessageResponse;
import com.example.Car.Services.DTO.response.RegistrationDocumentResponse;
import com.example.Car.Services.Interface.Admin.AdminRegistrationDocumentInterface;
import com.example.Car.Services.Repository.GarageRepository;
import com.example.Car.Services.Repository.RegistrationDocumentRepository;
import com.example.Car.Services.Repository.UserRepository;
import com.example.Car.Services.entities.RegistrationDocument;
import com.example.Car.Services.entities.User;
import com.example.Car.Services.entities.Garage;
import com.example.Car.Services.enums.GarageStatus;
import com.example.Car.Services.enums.RequestStatus;
import com.example.Car.Services.expection.BadRequestException;
import com.example.Car.Services.service.common.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class GarageRegistrationAdminService  implements AdminRegistrationDocumentInterface {

    private final UserRepository userRepository;
    private final RegistrationDocumentRepository documentRepository;
    private final EmailService emailService;
    private final GarageRepository garageRepository;


    @Transactional(readOnly = true)
    @Override
    public List<RegistrationDocumentResponse> getAllRegistrationDocuments() {
        log.info("Fetching all registration documents fo admin view");
        List<RegistrationDocument> documents = documentRepository.findAll();
        log.info("Successfully fetched {} registration documents", documents.size());
        return RegistrationDocumentResponse.fromEntity(documents);


    }


    @Transactional(readOnly = true)
    @Override
    public RegistrationDocumentResponse getRegistrationDocumentsById(Long id) {
        RegistrationDocument  registrationDocument = documentRepository
                    .findById(id).orElseThrow(() ->{
                        log.warn("Document NotFound: Document not found with ID: {}", id);
                        return new BadRequestException("Document not found with ID: " + id);
                    });

        log.info("Successfully retrieved registration document with ID: {}", id);
        return RegistrationDocumentResponse.fromEntity(registrationDocument);
    }


    @Transactional
    @Override
    public MessageResponse makeDecision(Long id, DecisionRequestDTO requestDTO) {

        RegistrationDocument registrationDocument = documentRepository
                .findById(id).orElseThrow(() ->{
                    log.warn("Document NotFound: Document not found: {}", id);
                    return new BadRequestException("Document not found with ID: " + id);
                });

        User owner = registrationDocument.getOwner();
        registrationDocument.setStatus(requestDTO.getStatus());

        if(requestDTO.getStatus() == RequestStatus.REJECTED){

            registrationDocument.setRejectionReason(requestDTO.getReason());

        } else if (requestDTO.getStatus() == RequestStatus.APPROVED) {

            registrationDocument.setRejectionReason(null);

            String verificationToken = UUID.randomUUID().toString();
            owner.setActive(true);
            owner.setVerificationToken(verificationToken);
            owner.setVerificationTokenExpiry(Instant.now().plusSeconds(84600));

            Garage garage = buildAndSaveGarage(registrationDocument, owner);
            garageRepository.save(garage);
            documentRepository.save(registrationDocument);
            userRepository.save(owner);

        }

        if(owner.getEmail() != null){
            try {
                if ("APPROVED".equalsIgnoreCase(requestDTO.getStatus().toString())) {
                    emailService.sendApprovalEmail(owner.getEmail(), owner.getFullName(),owner.getVerificationToken());
                } else if ("REJECTED".equalsIgnoreCase(requestDTO.getStatus().toString())) {
                    emailService.sendRejectionEmail(owner.getEmail(), owner.getFullName(), requestDTO.getReason());
                }
            }catch(Exception ex){
                log.error("Failed to send status update email to {}: {}", owner.getEmail(), ex.getMessage(), ex);
            }
        }
        return new MessageResponse("Decision saved and processed successfully for document ID: " + id);

    }


    private Garage buildAndSaveGarage(RegistrationDocument doc, User owner) {
        Garage garage = new Garage();
        garage.setOwnerId(owner.getId());
        garage.setName(doc.getGarageName());
        garage.setGovernorate(doc.getGovernorate());
        garage.setState(doc.getState());
        garage.setLatitude(doc.getLatitude());
        garage.setLongitude(doc.getLongitude());
        garage.setPhone(owner.getPhone());
        garage.setStatus(GarageStatus.ACTIVE);
        return garageRepository.save(garage);
    }
}


