package com.netcracker.educ.printing.model.entity;

import com.netcracker.educ.printing.model.bean.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="users", schema = "public")
@Data
public class User {
    @Id
    private UUID user_id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(length = 60)
    private String surname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    private String information;
}
