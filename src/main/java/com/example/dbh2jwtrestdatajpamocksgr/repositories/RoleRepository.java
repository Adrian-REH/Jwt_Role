package com.example.dbh2jwtrestdatajpamocksgr.repositories;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}