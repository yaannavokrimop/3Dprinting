package com.netcracker.educ.printing.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue
    private UUID id;

    @NonNull
    @Column(nullable = false)
    private String equipName;

    @NonNull
    @Column
    private String equipDesc;

    @NonNull
    @Column(nullable = false)
    private int height;

    @NonNull
    @Column(nullable = false)
    private int width;

    @NonNull
    @Column(nullable = false)
    private int length;

    @ManyToMany
    @JoinTable(
            name = "mat_equip",
            joinColumns = @JoinColumn(name = "equip_id"),
            inverseJoinColumns = @JoinColumn(name = "mat_id")
    )
    Set<Material> materials;
}
