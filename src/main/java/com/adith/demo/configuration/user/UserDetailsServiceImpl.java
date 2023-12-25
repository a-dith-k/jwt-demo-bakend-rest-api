package com.adith.demo.configuration.user;

import com.adith.demo.entities.UserEntity;
import com.adith.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Optional<UserEntity> user= userRepository.findByUsername(username);

        if (user.isPresent()&&username.equals(user.get().getUsername()))
             return new UserDetailsImpl(user.get());

        throw new UsernameNotFoundException("User not found");

    }
}
