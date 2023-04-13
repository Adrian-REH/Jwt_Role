package com.example.dbh2jwtrestdatajpamocksgr.services;



import com.example.dbh2jwtrestdatajpamocksgr.dto.UserDto;
import com.example.dbh2jwtrestdatajpamocksgr.entitites.User;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
    User findById(Long id);
}
