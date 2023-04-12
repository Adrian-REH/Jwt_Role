package com.example.dbh2jwtrestdatajpamocksgr.controllers;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.User;
import com.example.dbh2jwtrestdatajpamocksgr.repositories.UserRepository;
import com.example.dbh2jwtrestdatajpamocksgr.security.jwt.JwtTokenUtil;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.JwtResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.LoginRequest;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.MessageResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.RegisterRequest;
import com.example.dbh2jwtrestdatajpamocksgr.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest).getBody());
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {

        return ResponseEntity.ok(authService.register(signUpRequest).getBody());
    }


}












