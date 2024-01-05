package com.adith.demo.exceptions;

import com.adith.demo.models.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;

import javax.naming.ServiceUnavailableException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> BadCredentialsExceptionHandler(){

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED).body("Bad Credentials");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<LoginResponse> userExistsException(){



        return ResponseEntity.badRequest()
                .body(LoginResponse.of(HttpStatus.BAD_REQUEST.value(),"User with given username already exists"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Void> userNotFoundException(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Void> serviceNotAvailable(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> invalidInput(MethodArgumentNotValidException ex, BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();

       result.getFieldErrors().forEach((error)->{
           errorMap.put(error.getField(),error.getDefaultMessage());
       });


        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> accessDeniedException(){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> UnknownError(){
//
//        return ResponseEntity.internalServerError().body("Something Went Wrong");
//    }



}
