package com.adith.demo.services.user;

import com.adith.demo.exceptions.UserAlreadyExistsException;
import com.adith.demo.exceptions.UserNotFoundException;
import com.adith.demo.models.*;
import com.adith.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.util.List;

@Service
public interface UserService {


    public RegistrationResponse registerUser(RegistrationRequest request) throws UserAlreadyExistsException;

    String getAuthenticationToken(JwtRequest request);

    List<UserResponseDto> getAllUsers();

    UserProfileDto getUserByUsername(String username);

    void deleteUser(Integer id)throws UserNotFoundException;

    UserResponseDto getUserByUserId(Integer id);

    void updateUser(UserRequestDto request,Integer id) throws UserAlreadyExistsException;

    void updateProfile(UserProfileDto request);

    void createUser(UserRequestDto request) throws UserAlreadyExistsException;

    boolean isDuplicate(String username);
}
