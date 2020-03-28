package com.netcracker.educ.printing.model.entity;

import com.netcracker.educ.printing.model.bean.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
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

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 500)
    private String information;

   /* public User(String name, String surname, String email, String information, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.information = information;
        this.phone = phone;
    }*/
}
