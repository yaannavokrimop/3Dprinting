package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.bean.MaterialType;
import com.netcracker.educ.printing.model.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MaterialRepo extends JpaRepository<Material, UUID> {
    Material findByType(MaterialType type);
}
