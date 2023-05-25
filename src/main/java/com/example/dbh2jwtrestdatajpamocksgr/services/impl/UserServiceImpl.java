package com.example.dbh2jwtrestdatajpamocksgr.services.impl;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.Role;
import com.example.dbh2jwtrestdatajpamocksgr.entitites.User;
import com.example.dbh2jwtrestdatajpamocksgr.exception.EmailAlreadyExistsException;
import com.example.dbh2jwtrestdatajpamocksgr.exception.RegisterRequestParamNullException;
import com.example.dbh2jwtrestdatajpamocksgr.exception.UsernameAlreadyExistsException;
import com.example.dbh2jwtrestdatajpamocksgr.repositories.UserRepository;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.RegisterRequest;
import com.example.dbh2jwtrestdatajpamocksgr.services.RoleService;
import com.example.dbh2jwtrestdatajpamocksgr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoles().getId()));
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        if(id == null){ // programación defensiva
            throw new IllegalArgumentException("Valor id de usuario incorrecto, no es posible realizar la búsqueda.");
        }
        throw new NoSuchElementException("No se ha encontrado el usuario solicitado.");
    }

    /**
     * Check si no ingreso parametros nulos o en blanco
     * Check si el usuario existe
     * Check si el email existe
     *
     * codifico la contraseña
     *
     * Check su email para saber su rol y lo genero
     * Guardo el usuario y el rol
     *
     * Nota:
     * @param user
     * @return
     */
    @Override
    public User save(RegisterRequest user) {
        User nUser = user.getRegisterFromDto();

        // Check 1: ParamIsNull
        if (nUser.getPassword()==null||nUser.getUsername()==null||nUser.getEmail()==null)
            throw new RegisterRequestParamNullException("Ingrese una clave, usuario y/ email");

        // Check 2: ParamIsBlank
        if (nUser.getPassword().isBlank()||nUser.getUsername().isBlank()||nUser.getEmail().isBlank())
            throw new RegisterRequestParamNullException("Ingrese una clave, usuario y/ email");

        // Check 3: username
        if(userRepository.existsByUsername(nUser.getUsername()))
            throw new UsernameAlreadyExistsException("User  ocupado");

        // Check 4: email
        if(userRepository.existsByEmail(nUser.getEmail()))
            throw new EmailAlreadyExistsException("Email ocupado");


        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));


        Role role = roleService.findByName("ADMIN");

        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ATM");
        }


        nUser.setRoles(role);
        return userRepository.save(nUser);


    }


}
