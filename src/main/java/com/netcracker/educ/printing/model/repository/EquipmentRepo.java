package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipmentRepo extends JpaRepository<Equipment, UUID> {
    List<Equipment> findByEquipNameContaining(String name);
}
