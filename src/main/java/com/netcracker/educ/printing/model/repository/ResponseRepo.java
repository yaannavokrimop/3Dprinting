package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.bean.ResponseId;
import com.netcracker.educ.printing.model.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResponseRepo extends JpaRepository<Response, ResponseId> {
    List<Response> findAllByOrder_UserId(UUID userId);
}
