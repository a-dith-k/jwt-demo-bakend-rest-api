package com.adith.demo.controllers;
import com.adith.demo.models.UserDto;
import com.adith.demo.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public List<UserDto> getAllUsers(){

        return userService.getAllUsers();
    }

    @GetMapping("{username}")
    public ResponseEntity<UserDto> getAllUsers(@PathVariable String username){
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }
}
