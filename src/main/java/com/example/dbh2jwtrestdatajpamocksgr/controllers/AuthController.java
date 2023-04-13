package com.example.dbh2jwtrestdatajpamocksgr.controllers;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.User;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.JwtResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.LoginRequest;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.RegisterRequest;
import com.example.dbh2jwtrestdatajpamocksgr.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest signUpRequest) {
        return ResponseEntity.ok(authService.register(signUpRequest));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello-admin")
    public String adminPing(){
        return "Hola!, bienvenido a la seccion Administracion";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello-admin-user")
    public String adminUser(){
        return "Hola! Bienvenido a la seccion Usuario administrador";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello-user")
    public String userPing(){
        return "Hola! Bienvenido a la seccion Usuario";
    }



}












