package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    User findByName(String name);

    List<User> findByRole(Role role);
    Boolean existsByEmail(String email);
}
