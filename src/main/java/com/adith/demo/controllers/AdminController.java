package com.adith.demo.controllers;

import com.adith.demo.exceptions.UserAlreadyExistsException;
import com.adith.demo.exceptions.UserNotFoundException;
import com.adith.demo.models.UserRequestDto;
import com.adith.demo.models.UserResponseDto;
import com.adith.demo.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {

        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequestDto request)
            throws UserAlreadyExistsException {

        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.getUserByUserId(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody UserRequestDto request)
            throws UserNotFoundException, UserAlreadyExistsException {
        userService.updateUser(request, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
