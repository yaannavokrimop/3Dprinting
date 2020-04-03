package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {
    List<Order> findByDescriptionContaining(String desc);
}
