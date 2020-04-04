package com.netcracker.educ.printing.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
public class City {
    @Id
    private int cityId;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable (
            name = "address",
            joinColumns = {@JoinColumn(name = "city_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> users = new HashSet<>();
}
