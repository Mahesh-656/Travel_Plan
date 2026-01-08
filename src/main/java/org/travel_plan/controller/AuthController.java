package org.travel_plan.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.travel_plan.dto.AuthRequest;
import org.travel_plan.dto.AuthResponse;
import org.travel_plan.model.User;
import org.travel_plan.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> createUser(@RequestBody AuthRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(request));
    }
}
