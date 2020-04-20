package com.netcracker.educ.printing.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "executor_equipment")
@Data
public class ExecutorEquipment {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private boolean ready = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "executorId", nullable = false)
    private User executor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "equipmentId", nullable = false)
    private Equipment equipment;

    @ManyToMany
    @JoinTable(
            name = "exe_mat_equip",
            joinColumns = @JoinColumn(name = "exe_equip_id"),
            inverseJoinColumns = @JoinColumn(name = "mat_equip_id")
    )
    Set<MaterialEquipment> matEquips;

    public ExecutorEquipment(User executor, Equipment equipment) {
        this.equipment = equipment;
        this.executor = executor;
    }

    public ExecutorEquipment() {
    }
}
