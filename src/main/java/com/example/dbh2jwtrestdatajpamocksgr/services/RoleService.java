package com.example.dbh2jwtrestdatajpamocksgr.services;


import com.example.dbh2jwtrestdatajpamocksgr.entitites.Role;

public interface RoleService {
    Role findByName(String name);
}
