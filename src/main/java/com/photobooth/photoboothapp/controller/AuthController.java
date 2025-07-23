// File: src/main/java/com/photobooth/photoboothapp/controller/AuthController.java
package com.photobooth.photoboothapp.controller;

import com.photobooth.photoboothapp.model.User;
import com.photobooth.photoboothapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerNewUser(user);
            return ResponseEntity.ok("Registrasi berhasil! Silakan login, sayangku.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}