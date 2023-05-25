package com.example.dbh2jwtrestdatajpamocksgr.entitites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ob_role",schema = "security")
public class Role  {

    @Id
    @Column
    private String id;

    @Column
    private String description;

    @JsonIgnoreProperties("roles")
    @OneToMany(mappedBy = "roles")
    private List<User> user = new ArrayList<>();

    // getters y setters
}
