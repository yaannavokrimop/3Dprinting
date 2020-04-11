package com.netcracker.educ.printing.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.netcracker.educ.printing.model.bean.Role;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(length = 60)
    private String surname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 45)
    private String phone;

    @NaturalId
    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 500)
    private String information;

   /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Address> users = new HashSet<>();*/
    public User() { }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Address> addresses;
//    private Set<Address> users = new HashSet<>();

    public User(String name, String surname, String email, String information, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.information = information;
        this.phone = phone;
    }

    public User(String name, String surname, String email, String information, String phone,Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.information = information;
        this.phone = phone;
        this.role = role;
    }
}