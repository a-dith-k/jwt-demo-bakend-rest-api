package com.adith.demo.services.user;

import com.adith.demo.exceptions.UserAlreadyExistsException;
import com.adith.demo.models.JwtRequest;
import com.adith.demo.models.RegistrationRequest;
import com.adith.demo.models.RegistrationResponse;
import com.adith.demo.models.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public RegistrationResponse registerUser(RegistrationRequest request) throws UserAlreadyExistsException;

    String getAuthenticationToken(JwtRequest request);

    List<UserDto> getAllUsers();

    UserDto getUserByUsername(String username);
}
