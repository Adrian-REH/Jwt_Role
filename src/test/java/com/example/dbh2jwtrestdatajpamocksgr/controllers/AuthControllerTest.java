package com.example.dbh2jwtrestdatajpamocksgr.controllers;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.JwtResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.LoginRequest;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.MessageResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class AuthControllerTest {
    private final RegisterRequest registerRequest = new RegisterRequest();
    private final LoginRequest loginRequest = new LoginRequest();
    private final HttpHeaders headers = new HttpHeaders();

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.defaultHeader("Authorization","sd")
                .rootUri("http://localhost:"+port);
        testRestTemplate= new TestRestTemplate(restTemplateBuilder);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


    }



    @Test
    void whenLogin_thenReturnJwtResponse() {
        loginRequest.setUsername("adrian");
        loginRequest.setPassword("1234");
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);
        ResponseEntity<JwtResponse> response = testRestTemplate.postForEntity("/api/auth/login",request, JwtResponse.class);

        JwtResponse result = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertNotEquals(null, result.getToken());

    }

    @Test
    void whenLoginRequestNull_thenReturnJwtResponseNull() {
        ResponseEntity<JwtResponse> response = testRestTemplate.postForEntity("/api/auth/login",null, JwtResponse.class);

        JwtResponse result = response.getBody();
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertEquals(415, response.getStatusCodeValue());
        assertEquals(null, result.getToken());

        System.out.println(result);

    }

    @Test
    void whenRegister_thenReturnMessageResponse() {
        registerRequest.setNombre("adrian");
        registerRequest.setApellido("herrera");
        registerRequest.setUsername("adm");//Se guarda en el servidor por ende hay que tener cuidado
        registerRequest.setEmail("adr@gmail.com");
        registerRequest.setPassword("1234");
        HttpEntity<RegisterRequest> request = new HttpEntity<>(registerRequest, headers);

        ResponseEntity<MessageResponse> response = testRestTemplate.postForEntity("/api/auth/register",request, MessageResponse.class);

        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody().getMessage());

    }

    @Test
    void whenRegisterRequestNull_thenReturnMessageResponseUMT() {
        ResponseEntity<String > response = testRestTemplate.postForEntity("/api/auth/login",null, String.class);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertEquals(415, response.getStatusCodeValue());

    }

    @Test
    void whenRegisterRequestParmNull_thenReturnMessageResponseISE() {
        registerRequest.setNombre(null);
        registerRequest.setApellido("");
        registerRequest.setUsername(null);
        registerRequest.setEmail(null);
        registerRequest.setPassword(null);
        HttpEntity<RegisterRequest> request = new HttpEntity<>(registerRequest, headers);

        ResponseEntity<MessageResponse> response = testRestTemplate.postForEntity("/api/auth/register",request, MessageResponse.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Error: get into User, Email and/or Password", response.getBody().getMessage());


    }


    @Test
    void whenRegisterRequestParmIsBlank_thenReturnMessageResponseISE() {
        registerRequest.setNombre("");
        registerRequest.setApellido("");
        registerRequest.setUsername("");
        registerRequest.setEmail("");
        registerRequest.setPassword("");
        HttpEntity<RegisterRequest> request = new HttpEntity<>(registerRequest, headers);

        ResponseEntity<MessageResponse> response = testRestTemplate.postForEntity("/api/auth/register",request, MessageResponse.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Error: get into User, Email and/or Password", response.getBody().getMessage());


    }

    @Test
    void whenRegisterUsernameExist_thenReturnMessageResponseUsernameExist() {
        registerRequest.setNombre(null);
        registerRequest.setApellido(null);
        registerRequest.setUsername("adrian");
        registerRequest.setEmail("@");
        registerRequest.setPassword("1234");
        HttpEntity<RegisterRequest> request = new HttpEntity<>(registerRequest, headers);

        ResponseEntity<MessageResponse > response = testRestTemplate.postForEntity("/api/auth/register",request, MessageResponse.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Error: Username is already taken!", response.getBody().getMessage());

    }
    @Test
    void whenRegisterEmailExist_thenReturnMessageResponseEmailExist() {
        registerRequest.setNombre(null);
        registerRequest.setApellido(null);
        registerRequest.setUsername("dex");
        registerRequest.setEmail("adrian@gmail.com");
        registerRequest.setPassword("1234");
        HttpEntity<RegisterRequest> request = new HttpEntity<>(registerRequest, headers);

        ResponseEntity<MessageResponse > response = testRestTemplate.postForEntity("/api/auth/register",request, MessageResponse.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Error: Email is already in use!", response.getBody().getMessage());

    }
}