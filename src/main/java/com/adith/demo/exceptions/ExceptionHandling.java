package com.adith.demo.exceptions;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> BadCredentialsExceptionHandler(){

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED).body("Bad Credentials");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> UnknownError(){

        return ResponseEntity.internalServerError().body("Something Went Wrong");
    }


}
