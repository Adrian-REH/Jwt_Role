package com.example.dbh2jwtrestdatajpamocksgr.repositories;

import com.example.dbh2jwtrestdatajpamocksgr.entitites.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryDataJpaTestIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindByUsername_thenReturnUser(){
        User user = createUser();
        entityManager.persist(user);
        entityManager.flush();

        User b = userRepository.findByUsername("Adrian").orElseThrow();
        assertThat(b.getUsername())
                .isEqualTo(user.getUsername());

    }
    @Test
    public void whenExistsByEmail_thenReturnBoolean(){
        User user = createUser();
        entityManager.persist(user);
        entityManager.flush();


        assertThat(userRepository.existsByEmail("adrian@gmail.com"))
                .isEqualTo(user.getEmail().contentEquals("adrian@gmail.com"));

    }
    @Test
    public void whenExistsByUsername_thenReturnBoolean(){
        User user = createUser();
        entityManager.persist(user);
        entityManager.flush();


        assertThat(userRepository.existsByEmail("Adrian"))
                .isEqualTo(user.getEmail().contentEquals("Adrian"));

    }

    private User createUser() {
        User user = new User();
        user.setApellido("");
        user.setNombre("");
        user.setUsername("Adrian");
        user.setEmail("adrian@gmail.com");
        user.setPassword("");
        return user;
    }
}
