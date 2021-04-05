package com.netcracker.educ.printing.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "executor_equipment")
@Data
public class ExecutorEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private boolean ready = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "executorId", nullable = false)
    private User executor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "equipmentId", nullable = false)
    private Equipment equipment;

    public ExecutorEquipment(User executor, Equipment equipment) {
        this.equipment = equipment;
        this.executor = executor;
    }

    public ExecutorEquipment() {
    }
}
