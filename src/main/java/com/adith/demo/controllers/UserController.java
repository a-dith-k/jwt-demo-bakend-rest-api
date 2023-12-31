package com.adith.demo.controllers;
import com.adith.demo.models.UserProfileDto;
import com.adith.demo.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("{username}")
    public ResponseEntity<UserProfileDto> getUser(@PathVariable String username){
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @PostMapping()
    public ResponseEntity<Void> updateUser( @RequestBody UserProfileDto request){

        userService.updateProfile(request);
        return ResponseEntity.ok().build();
    }




}
