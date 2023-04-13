package com.example.dbh2jwtrestdatajpamocksgr.repositories;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "/application-test.properties")
class RoleRepositoryDataJpaTestIT {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    void findRoleByName() {


        Role b = roleRepository.findRoleByName("ADMIN");
        assertThat(b.getName())
                .isEqualTo("ADMIN");

         b = roleRepository.findRoleByName("USER");
        assertThat(b.getName())
                .isEqualTo("USER");

         b = roleRepository.findRoleByName("MANAGER");
        assertThat(b.getName())
                .isEqualTo("MANAGER");

    }

}