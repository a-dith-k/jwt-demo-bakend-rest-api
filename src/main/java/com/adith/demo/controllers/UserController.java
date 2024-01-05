package com.adith.demo.controllers;
import com.adith.demo.models.UserProfileDto;
import com.adith.demo.services.user.UserService;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("test")
    public ResponseEntity<Void> testAuthentication(){

        return ResponseEntity.ok().build();
    }
    @GetMapping("{username}")
    public ResponseEntity<UserProfileDto> getUser(@PathVariable String username){
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @PutMapping()
    public ResponseEntity<Void> updateUser(@RequestBody UserProfileDto request){
        System.out.println("reached");
        userService.updateProfile(request);
        return ResponseEntity.ok().build(); 
    }

    @PostMapping("/profile-image")
    public ResponseEntity<Void> uploadProfileImage(@RequestParam MultipartFile image ,@RequestParam Integer id) {
        System.out.println(image.getOriginalFilename());
        try{
            userService.updateProfileImage(image,id);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile-image")
    public ResponseEntity<?> getProfileImage(@RequestParam Integer userId) {
        
       return  ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/jpeg"))
				.body(userService.getProfileImage(userId));
    }
    
    




}
