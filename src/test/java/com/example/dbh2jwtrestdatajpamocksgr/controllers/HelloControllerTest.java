package com.example.dbh2jwtrestdatajpamocksgr.controllers;

import com.example.dbh2jwtrestdatajpamocksgr.security.payload.JwtResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.LoginRequest;
import com.sun.jdi.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerTest {
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

        headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZHJpYW4iLCJpYXQiOjE2ODEzMTI4NjAsImV4cCI6MTY4MTM5OTI2MH0.AUGXPt_lrBmf2sjXoB64KPosj4bmepntBnle-SQ9tiQD8kQJMy6U1aV_6eA_1rP0sUoQY4QwI5Dp92Z5dFVkSQ");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


    }

    @Test
    void whenHello_thenReturnHello(){
        HttpEntity<LoginRequest> request = new HttpEntity<>( headers);
        ResponseEntity<String> response = testRestTemplate.exchange("/api/hello", HttpMethod.GET,request, String.class);

        String result = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hola! Accediste a mi api correctamente", result);

    }

    @Test
    void whenHelloHeadersNull_thenReturnUnauthorized(){
        HttpEntity<LoginRequest> request = new HttpEntity<>( null);
        ResponseEntity<String> response = testRestTemplate.exchange("/api/hello", HttpMethod.GET,request, String.class);

        String result = response.getBody();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(401, response.getStatusCodeValue());
        assertNotEquals("Hola! Accediste a mi api correctamente", result);

    }
}