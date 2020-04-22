package com.netcracker.educ.printing.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
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
    @Column(nullable = false)
    private int height;

    @NonNull
    @Column(nullable = false)
    private int width;

    @NonNull
    @Column(nullable = false)
    private int length;

    @OneToMany(mappedBy = "equipment")
    Set<MaterialEquipment> materialEquipments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return height == equipment.height &&
                width == equipment.width &&
                length == equipment.length &&
                Objects.equals(id, equipment.id) &&
                Objects.equals(equipName, equipment.equipName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, equipName, height, width, length);
    }
}
