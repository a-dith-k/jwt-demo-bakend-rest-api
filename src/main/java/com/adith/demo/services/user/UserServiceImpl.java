package com.adith.demo.services.user;

import com.adith.demo.entities.UserEntity;
import com.adith.demo.exceptions.UserAlreadyExistsException;
import com.adith.demo.models.JwtRequest;
import com.adith.demo.models.RegistrationRequest;
import com.adith.demo.models.RegistrationResponse;
import com.adith.demo.repositories.UserRepository;
import com.adith.demo.services.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private  JwtService jwtService;;
    final private PasswordEncoder passwordEncoder;
    final private AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public RegistrationResponse registerUser(RegistrationRequest request) throws UserAlreadyExistsException {
        Optional<UserEntity> existingUser
                = userRepository.findByUsername(request.username());

        if(existingUser.isPresent())
            throw new UserAlreadyExistsException("User name already exists");

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

        String token=
                jwtService.generateToken(request.username());

        if(authentication.isAuthenticated()&&token!=null)
            return token;
        else
            throw new  BadCredentialsException("Bad Credentials");


    }
}
