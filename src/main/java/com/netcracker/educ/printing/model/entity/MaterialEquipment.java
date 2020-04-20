package com.netcracker.educ.printing.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "mat_equip")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class MaterialEquipment {
    @Id
    @GeneratedValue
    private UUID id;

    @JsonIgnore
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "equip_id", nullable = false)
    private Equipment equipment;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mat_id", nullable = false)
    private Material material;


    @ManyToMany(mappedBy = "matEquips")
    Set<ExecutorEquipment> exeEquips;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialEquipment that = (MaterialEquipment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
