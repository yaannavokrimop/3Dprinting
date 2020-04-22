package com.netcracker.educ.printing.model.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Objects;
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

    @NonNull
    @Column(nullable = false)
    private String equipDesc;

//    public ExecutorEquipment(User executor, Equipment equipment) {
//        this.equipment = equipment;
//        this.executor = executor;
//    }

    public ExecutorEquipment(User executor, Equipment equipment,String equipDesc) {
        this.equipment = equipment;
        this.executor = executor;
        this.equipDesc=equipDesc;
    }

    public ExecutorEquipment() {
    }
}
