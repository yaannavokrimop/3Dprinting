package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {
    List<Order> findByDescriptionContaining(String desc);
    List<Order>findByUserId(UUID user_id);
}