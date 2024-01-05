package com.adith.demo.services.user;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adith.demo.exceptions.UserAlreadyExistsException;
import com.adith.demo.exceptions.UserNotFoundException;
import com.adith.demo.models.JwtRequest;
import com.adith.demo.models.RegistrationRequest;
import com.adith.demo.models.RegistrationResponse;
import com.adith.demo.models.UserProfileDto;
import com.adith.demo.models.UserRequestDto;
import com.adith.demo.models.UserResponseDto;

@Service
public interface UserService {

    public RegistrationResponse registerUser(RegistrationRequest request) throws UserAlreadyExistsException;

    String getAuthenticationToken(JwtRequest request);

    List<UserResponseDto> getAllUsers();

    UserProfileDto getUserByUsername(String username);

    void deleteUser(Integer id) throws UserNotFoundException;

    UserResponseDto getUserByUserId(Integer id);

    void updateUser(UserRequestDto request, Integer id) throws UserAlreadyExistsException;

    void updateProfile(UserProfileDto request);

    void createUser(UserRequestDto request) throws UserAlreadyExistsException;

    boolean isDuplicate(String username);

    public void updateProfileImage(MultipartFile image, Integer id) throws IOException;
    
    public byte[] getProfileImage(Integer userId);
}
