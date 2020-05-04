package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.Material;
import com.netcracker.educ.printing.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    List<User> findByRole(Role role);

    Boolean existsByEmail(String email);

    @Query("SELECT distinct ex from User ex " +
            "join ex.addresses addr " +
            "join addr.city city " +
            "join ExecutorEquipment equipments on ex = equipments.executor " +
            "join equipments.equipment equipment " +
            "join equipment.materialEquipments matEquip " +
            "join matEquip.material material " +
            "where (city in (:cityList) or :cityList is null) " +
            "and (equipment.height >= :height and equipment.width >= :width and equipment.length >= :length) " +
            "and (material in (:materials) or (:materials) is null) " +
            "and ex.role = :role")
    Page<User> findAllByParamsWithMaterials(@Param(value = "cityList") List<City> cityList,
                                            @Param(value = "height") int height,
                                            @Param(value = "width") int width,
                                            @Param(value = "length") int length,
                                            @Param(value = "materials") List<Material> materials,
                                            @Param(value = "role") Role role, Pageable page);

    @Query("SELECT distinct ex from User ex " +
            "join ex.addresses addr " +
            "join addr.city city " +
            "join ExecutorEquipment equipments on ex = equipments.executor " +
            "join equipments.equipment equipment " +
            "where (city in (:cityList) or :cityList is null) " +
            "and (equipment.height >= :height and equipment.width >= :width and equipment.length >= :length) " +
            "and ex.role = :role")
    Page<User> findAllByParams(@Param(value = "cityList") List<City> cityList,
                               @Param(value = "height") int height,
                               @Param(value = "width") int width,
                               @Param(value = "length") int length,
                               @Param(value = "role") Role role, Pageable page);
}
