package com.netcracker.educ.printing.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.educ.printing.model.bean.MaterialType;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
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

    /*@JsonIgnore
    @ManyToMany(mappedBy = "materials")
    Set<Equipment> equips;*/

    @JsonIgnore
    @ManyToMany(mappedBy = "materials")
    Set<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Objects.equals(id, material.id) &&
                Objects.equals(matTitle, material.matTitle) &&
                type == material.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matTitle, type);
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", matTitle='" + matTitle + '\'' +
                ", type=" + type +
                '}';
    }
}
