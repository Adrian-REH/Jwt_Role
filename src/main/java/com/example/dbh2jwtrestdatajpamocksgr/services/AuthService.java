package com.example.dbh2jwtrestdatajpamocksgr.services;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.User;
import com.example.dbh2jwtrestdatajpamocksgr.repositories.UserRepository;
import com.example.dbh2jwtrestdatajpamocksgr.security.jwt.JwtTokenUtil;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.JwtResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.LoginRequest;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.MessageResponse;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * Controlador para llevar a cabo la autenticación utilizando JWT
 *
 * Se utiliza AuthenticationManager para autenticar las credenciales que son el
 * usuario y password que llegan por POST en el cuerpo de la petición
 *
 * Si las credenciales son válidas se genera un token JWT como respuesta
 */
@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(AuthenticationManager authManager,
                          UserRepository userRepository,
                          PasswordEncoder encoder,
                          JwtTokenUtil jwtTokenUtil){
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<JwtResponse> login( LoginRequest loginRequest){

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenUtil.generateJwtToken(authentication);


        return ResponseEntity.ok(new JwtResponse(jwt));
    }




    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {

        if (signUpRequest.getPassword()==null||signUpRequest.getUsername()==null||signUpRequest.getEmail()==null){
            return ResponseEntity.ok(new MessageResponse("Error: get into User, Email and/or Password"));
        }
        if (signUpRequest.getPassword().isBlank()||signUpRequest.getUsername().isBlank()||signUpRequest.getEmail().isBlank()){
            return ResponseEntity.ok(new MessageResponse("Error: get into User, Email and/or Password"));

        }
            // Check 1: username
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Check 2: email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Creo un nuevo usuario
        User user = new User(null,signUpRequest.getUsername(),signUpRequest.getNombre(),signUpRequest.getApellido(),signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Es demasiado inseguro para aplicarlo en produccion, deberia enviar un email...
     * @param signUpRequest
     * @return
     */
    public ResponseEntity<MessageResponse> update(@RequestBody RegisterRequest signUpRequest) {
        User user = new User(null,signUpRequest.getUsername(),signUpRequest.getNombre(),signUpRequest.getApellido(),signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()));

        // Check 1: username
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User modified successfully!"));

        }

        // Check 2: email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User modified successfully!"));

        }


        return ResponseEntity.ok(new MessageResponse("Error: User not registered"));
    }

    /**
     * Es demasiado inseguro para aplicarlo en produccion, deberia enviar un email...
     * @param signUpRequest
     * @return
     */
    public ResponseEntity<MessageResponse> delete(@RequestBody RegisterRequest signUpRequest) {
        User user = new User(null,signUpRequest.getUsername(),signUpRequest.getNombre(),signUpRequest.getApellido(),signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()));

        // Check 1: username
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            userRepository.delete(user);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));

        }

        // Check 2: email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            userRepository.delete(user);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));

        }


        return ResponseEntity.ok(new MessageResponse("Error: User not registered"));
    }




}
