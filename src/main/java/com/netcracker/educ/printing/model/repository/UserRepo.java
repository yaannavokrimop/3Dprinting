package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    User findByName(String name);

    List<User> findByRole(Role role);
    Boolean existsByEmail(String email);

//    @Query("SELECT ex from User ex  where ex.role=:role")
//    List<User>findByRoleAndAddressesCityName(@Param(value = "role") Role role);
  @Query("SELECT distinct ex from User ex join ex.addresses addr join addr.city city where city=:title and ex.role=:role")
    List<User>findByRoleAndAddressesCityName(@Param(value = "title") City title, @Param(value = "role") Role role);
}
