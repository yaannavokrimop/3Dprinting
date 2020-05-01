package com.netcracker.educ.printing.model.repository;


import com.netcracker.educ.printing.model.bean.ResponseId;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.Response;
import com.netcracker.educ.printing.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResponseRepo extends JpaRepository<Response, ResponseId> {
    Response findByOrderAndExecutor (Order order, User executor);
    Integer countDistinctByOrderId(UUID orderId);
    Page<Response> findAllByOrderId(UUID orderId, Pageable page);
}
