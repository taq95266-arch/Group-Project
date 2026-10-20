package com.example.Car.Services.DTO.response;

import com.example.Car.Services.enums.ServiceRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
<<<<<<< HEAD
import lombok.NoArgsConstructor;
=======
>>>>>>> origin/main
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
<<<<<<< HEAD
@Getter
@Setter
@NoArgsConstructor
=======

@Getter
@Setter
>>>>>>> origin/main
@AllArgsConstructor
public class ServiceCustomerRequestResponse {

    private Long requestId;

<<<<<<< HEAD
    private Long garageId;

    private String garageName;
=======
    private Long garageOptionId;
>>>>>>> origin/main

    private String guestName;

    private String guestPhone;

<<<<<<< HEAD
    private String guestEmail;

=======
>>>>>>> origin/main
    private String carMakeModel;

    private String carPlateNumber;

    private BigDecimal appliedPrice;

    private ServiceRequestStatus status;

<<<<<<< HEAD
    private String rejectionReason;

    private LocalDateTime createdAt;

    private BigDecimal latitude;

    private BigDecimal longitude;

}
=======
    private LocalDateTime createdAt;
}
>>>>>>> origin/main
