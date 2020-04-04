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
    private int id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<Address> cities = new HashSet<>();
}
