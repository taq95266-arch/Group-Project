package com.example.Car.Services.expection;


import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;

import java.time.Instant;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

       @ExceptionHandler(BadCredentialException.class)
     public ResponseEntity<Map<String,Object>> handleBadCredentialsException(BadCredentialException ex){
            log.warn("BadCredentials : {}", ex.getMessage(),ex );
            return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
       }



    @ExceptionHandler(AccountDeactivatedException.class)
    public ResponseEntity<Map<String,Object>> handleAccountDeactivatedException(AccountDeactivatedException ex){
        log.warn("AccountDeactivatedException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }


    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<Map<String,Object>> handleEmailNotVerifiedException(EmailNotVerifiedException ex){
        log.warn("EmailNotVerifiedException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<Map<String,Object>> handleEmailSendingException(EmailSendingException ex){
        log.warn("EmailSendingException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }



    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidCredentialsException(InvalidCredentialsException ex){
        log.warn("InvalidCredentialsException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFoundException(ResourceNotFoundException ex){
        log.warn("ResourceNotFoundException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }



    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidTokenException(InvalidTokenException ex){
        log.warn("InvalidTokenException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidRoleException(InvalidRoleException ex){
        log.warn("InvalidRoleException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        log.warn("EmailAlreadyExistsException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handlePhoneAlreadyExistsException(PhoneAlreadyExistsException ex){
        log.warn("PhoneAlreadyExistsException : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object> > handelValidationException(MethodArgumentNotValidException ex){
           String message =
                   ex.getBindingResult().getFieldErrors().stream()
                           .findFirst()
                           .map(DefaultMessageSourceResolvable::getDefaultMessage)
                           .orElse("Invalid request");


           HttpStatus status = HttpStatus.BAD_REQUEST;
           return ResponseEntity.status(status)
                   .body(Map.of("timestamp", Instant.now(),
                           "status", status.value(),
                           "error", message));


    }


    @ExceptionHandler({AsyncRequestNotUsableException.class, ClientAbortException.class})
    public void handleClientAbort(Exception ex){
           log.debug("Client closed connection during streaming (expected for video seeking/buffering) : {}" , ex.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGeneric(Exception ex){
        log.warn("Exception : {}", ex.getMessage(),ex );
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }


    private ResponseEntity<Map<String,Object>> buildResponse(HttpStatus stauts, String message){
           Map<String, Object> body = Map.of("timestamp", Instant.now(),"error" , message);

           return ResponseEntity.status(stauts).body(body);
       }






}
