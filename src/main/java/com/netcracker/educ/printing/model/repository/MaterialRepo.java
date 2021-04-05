package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.bean.MaterialType;
import com.netcracker.educ.printing.model.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaterialRepo extends JpaRepository<Material, UUID> {
    Material findByType(MaterialType type);
    Material findByMatTitle(String matTitle);
    @Query("select m.matTitle from Material m where lower(m.matTitle) like lower(concat('%',:title,'%'))")
    List<String> findMatTitleByMatTitleContaining(@Param(value = "title") String titlePart);

    @Query("select m.matTitle from Material m")
    List<String> findAllMatTitles();

//    @Query("select m.matTitle from Material m join MaterialEquipment matEquip on matEquip.material=m " +
//            "join ExecutorEquipment exEquip on exEquip.equipment=matEquip.equipment " +
//            "where exEquip.executor=:userId")
//    @Query("select m.matTitle from Material m join ExecutorEquipment exEquip where exEquip.matEquips and exEquip.executor.id=:userId")
//    List<String> findMatTitleByUserId(@Param(value = "userId") UUID userId);

}
