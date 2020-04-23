package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ChatRepo extends JpaRepository<Chat, UUID> {
    boolean existsByExecutorAndCustomer(User executor, User customer);
}
