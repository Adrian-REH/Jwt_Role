package com.example.dbh2jwtrestdatajpamocksgr.services;



import com.example.dbh2jwtrestdatajpamocksgr.entitites.User;
import com.example.dbh2jwtrestdatajpamocksgr.security.payload.RegisterRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserService {
    User save(RegisterRequest user);
    List<User> findAll();
    User findOne(String username);
    User findById(Long id);
}
