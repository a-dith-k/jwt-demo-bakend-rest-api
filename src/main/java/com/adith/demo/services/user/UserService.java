package com.adith.demo.services.user;

import com.adith.demo.exceptions.UserAlreadyExistsException;
import com.adith.demo.models.JwtRequest;
import com.adith.demo.models.RegistrationRequest;
import com.adith.demo.models.RegistrationResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public RegistrationResponse registerUser(RegistrationRequest request) throws UserAlreadyExistsException;

    String getAuthenticationToken(JwtRequest request);
}
