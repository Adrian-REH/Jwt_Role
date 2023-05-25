package com.example.dbh2jwtrestdatajpamocksgr.controllers;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.User;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.JwtResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.LoginRequest;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.RegisterRequest;
import com.example.dbh2jwtrestdatajpamocksgr.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<User>> findAll(){
        return ResponseEntity.ok(authService.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest signUpRequest) {
        return ResponseEntity.ok(authService.register(signUpRequest));
    }

/*

    @GetMapping("/hello-admin")
    public ResponseEntity<ByteArrayResource>  adminHello()  {
        generateInvoice();
        ByteArrayOutputStream outputStream = generateInvoice();

        // Crea un recurso ByteArrayResource a partir del flujo de salida del PDF
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        // Configura las cabeceras de la respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf");

        // Devuelve la respuesta con el recurso PDF, las cabeceras y el tipo de contenido adecuado
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(outputStream.size())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);

    }
*/

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello-admin-user")
    public String adminUserHello(){
        return "Hola! Bienvenido a la seccion Usuario administrador";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello-user")
    public String userHello(){
        return "Hola! Bienvenido a la seccion Usuario";
    }



}












