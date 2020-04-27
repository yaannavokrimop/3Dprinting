package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.Equipment;
import com.netcracker.educ.printing.model.entity.ExecutorEquipment;
import com.netcracker.educ.printing.model.entity.MaterialEquipment;
import com.netcracker.educ.printing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExecutorEquipmentRepo extends JpaRepository<ExecutorEquipment, UUID> {
    List<ExecutorEquipment> findAllByExecutorId(UUID executorId);
    List<ExecutorEquipment> findByExecutorIdAndEquipmentId(UUID executorId,UUID equipmentId);


    @Query("select exEquip.matEquips from ExecutorEquipment exEquip where exEquip.executor.id=:executorId ")
    List<MaterialEquipment> findMaterialEquipmentByExecutorId(@Param(value = "executorId") UUID executorId);

}
