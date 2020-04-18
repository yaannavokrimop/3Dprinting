package com.netcracker.educ.printing.model.entity;

import com.netcracker.educ.printing.model.bean.MaterialType;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Material {
    @Id
    @GeneratedValue
    private UUID id;

    @NonNull
    @Column(nullable = false)
    private String matTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialType type;

    @ManyToMany(mappedBy = "materials")
    Set<Equipment> equips;

    @ManyToMany(mappedBy = "materials")
    Set<Order> orders;
}
