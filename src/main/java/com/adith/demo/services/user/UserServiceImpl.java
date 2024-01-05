package com.adith.demo.services.user;

import com.adith.demo.configuration.user.UserDetailsServiceImpl;
import com.adith.demo.entities.ProfileImageEntity;
import com.adith.demo.entities.UserEntity;
import com.adith.demo.exceptions.UserAlreadyExistsException;
import com.adith.demo.exceptions.UserNotFoundException;
import com.adith.demo.models.*;
import com.adith.demo.repositories.ProfileImageRepository;
import com.adith.demo.repositories.UserRepository;
import com.adith.demo.services.jwt.JwtService;
import com.adith.demo.utils.ImageUtil;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.http.UserDetailsServiceFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    final private ModelMapper modelMapper;
    @Autowired
    UserDetailsService service;

    final private UserRepository userRepository;
    final private  JwtService jwtService;;
    final private PasswordEncoder passwordEncoder;
    final private AuthenticationManager authenticationManager;
    final private ProfileImageRepository profileImageRepository;

    public UserServiceImpl(     ModelMapper modelMapper, 
                                UserRepository userRepository,
                                 JwtService jwtService,
                                  PasswordEncoder passwordEncoder,
                                   AuthenticationManager authenticationManager,
                                   ProfileImageRepository profileImageRepository
                        ) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.profileImageRepository=profileImageRepository;
    }


    @Override
    public RegistrationResponse registerUser(RegistrationRequest request) throws UserAlreadyExistsException {
        Optional<UserEntity> existingUser
                = userRepository.findByUsername(request.username());

        if(existingUser.isPresent())
            throw   new UserAlreadyExistsException("User name already exists");

        UserEntity user=new UserEntity();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        UserEntity savedUser =userRepository
                                .save(user);

        return new RegistrationResponse(savedUser.getUserId(),
                                      savedUser.getUsername());
    }

    @Override
    public String getAuthenticationToken(JwtRequest request) throws AuthenticationException {

        Authentication authentication=
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken
                                            (request.username(),request.password())
                        );
        
        
       UserDetails userDetails= service.loadUserByUsername(request.username());
        String token=
                jwtService.generateToken(userDetails);

        if(authentication.isAuthenticated()&&token!=null)
            return token;
        else
            throw new  BadCredentialsException("Bad Credentials");


    }

    @Override
    public List<UserResponseDto> getAllUsers() {

      return   userRepository.findAll()
              .stream()
              .map(user-> modelMapper.map(user,UserResponseDto.class))
              .toList();
    }

    @Override
    public UserProfileDto getUserByUsername(String username) {
        System.out.println("Called");
        UserEntity user
                =userRepository
                .findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User does not exist"));
        return
                modelMapper
                        .map(user, UserProfileDto.class);
    }

    @Override
    public void deleteUser(Integer id)throws UserNotFoundException {
        UserEntity user
                =userRepository
                .findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserResponseDto getUserByUserId(Integer id) {
        UserEntity existingUser
                =userRepository
                .findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        existingUser.setPassword(null);
        return modelMapper.map(existingUser, UserResponseDto.class);
    }

    @Override
    public void updateUser(UserRequestDto request,Integer id) throws UserNotFoundException, UserAlreadyExistsException {
        UserEntity existingUser=userRepository
                .findById(
                        id
                ).orElseThrow(()->new UserNotFoundException("User not Found"));

        System.out.println(request);

        if(!request.getUsername().equals(existingUser.getUsername())
                &&isDuplicate(request.getUsername())
        ){
            throw new UserAlreadyExistsException("User already Exists");
        }

        request
                .setPassword(
                        passwordEncoder.encode(request.getPassword())
                );
         modelMapper.map(request, existingUser);

        userRepository.save(existingUser);
    }

    @Override
    public void updateProfile(UserProfileDto request) {
           UserEntity existingUser= userRepository
                    .findById(
                            request.getId()
                    ).orElseThrow(()->new UserNotFoundException("User Not Found"));

          modelMapper.map(request,existingUser);
           
           userRepository.save(existingUser);
    }

    @Override
    public void createUser(UserRequestDto request) throws UserAlreadyExistsException {
        if(isDuplicate(request.getUsername()))
            throw new UserAlreadyExistsException("User already Exists");

        request
                .setPassword(
                        passwordEncoder.
                                encode(request.getPassword())
                );
        userRepository
                .save(
                        modelMapper.map(request,UserEntity.class)
                );
    }

    @Override
    public boolean isDuplicate(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void updateProfileImage(MultipartFile image,Integer userId) throws IOException{
        try{
                 ProfileImageEntity profileImage
                        =ProfileImageEntity.of(1,userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found")),image.getOriginalFilename(),image.getContentType(),image.getBytes());
                                        // .id(1)
                                        // .user(userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found")))
                                        // .name(image.getOriginalFilename())
                                        // .type(image.getContentType())
                                        // .bytes()
                                        // .build();
                profileImageRepository.save(profileImage);  
        }catch(UserNotFoundException ue){
                log.error("Exception", ue.getMessage());
        }catch(Error e){
              log.error(e.getMessage());
        }
       
    }

        @Override
        public byte[] getProfileImage(Integer userId){
                
              ProfileImageEntity profileImageEntity= profileImageRepository.findByUser(userRepository.findById(userId).get());

              return profileImageEntity.getBytes()!=null? profileImageEntity.getBytes():null;
        }


}
