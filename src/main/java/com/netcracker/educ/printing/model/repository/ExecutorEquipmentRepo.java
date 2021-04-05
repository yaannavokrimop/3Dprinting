package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.ExecutorEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExecutorEquipmentRepo extends JpaRepository<ExecutorEquipment, UUID> {
    List<ExecutorEquipment> findAllByExecutorId(UUID executorId);

    ExecutorEquipment findByExecutorIdAndEquipmentId(UUID executorId,UUID equipmentId);
}
