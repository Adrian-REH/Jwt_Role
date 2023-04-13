package com.example.dbh2jwtrestdatajpamocksgr.services.impl;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.Role;
import com.example.dbh2jwtrestdatajpamocksgr.repositories.RoleRepository;
import com.example.dbh2jwtrestdatajpamocksgr.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
