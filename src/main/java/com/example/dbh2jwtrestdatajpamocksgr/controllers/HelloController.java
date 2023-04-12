package com.example.dbh2jwtrestdatajpamocksgr.controllers;

import com.example.dbh2jwtrestdatajpamocksgr.security.payload.JwtResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hola! Accediste a mi api correctamente");
    }
}
