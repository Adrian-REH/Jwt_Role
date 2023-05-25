package com.example.dbh2jwtrestdatajpamocksgr.entitites;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="ob_user", schema = "security")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User  implements Serializable{


    @Id
    @Column
    private String username;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String email;
    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role roles;



}