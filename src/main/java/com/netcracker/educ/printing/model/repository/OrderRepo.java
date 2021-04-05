package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {
    Page<Order> findAllByNameContainingAndUserId(String name, UUID id, Pageable page);
    List<Order>findByUserId(UUID user_id);
    Page<Order> findAllByUserId(UUID userId, Pageable page);
    Optional<Order> findByFile(String fileName);
}
