package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.MaterialEquipment;
import com.netcracker.educ.printing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Repository
public interface MaterialEquipmentRepo extends JpaRepository<MaterialEquipment, UUID> {

    @Query("select me.material.matTitle from MaterialEquipment me where me.equipment.id=:equipmentId and lower(me.material.matTitle) like lower(concat('%',:title,'%'))")
    List<String> findMatTitleByEquipmentIdAndMatTitleContaining(@Param(value = "equipmentId") UUID equipmentId,@Param(value = "title") String matTitle);

    @Query("select me from MaterialEquipment me where me.material.matTitle in :matName and me.equipment.equipName=:equipName")
    Set<MaterialEquipment> findByMaterialNames(@Param(value = "matName") List<String> matName,@Param(value = "equipName") String equipName);

    @Query("select ee.matEquips from MaterialEquipment me join ExecutorEquipment ee  where me.equipment.id=:equipId and ee.executor.id=:userId  ")
    List<MaterialEquipment> findMaterialByEquipmentAndUser(@Param(value = "userId") UUID userId,
                                                @Param(value = "equipId") UUID equipId);

    @Query("select me.material.matTitle from MaterialEquipment me where me.equipment.id=:equipmentId")
    List<String> findMatTitleByEquipmentId(@Param(value = "equipmentId") UUID equipId);
}
