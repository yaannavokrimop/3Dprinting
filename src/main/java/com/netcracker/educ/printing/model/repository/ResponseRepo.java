package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.bean.ResponseId;
import com.netcracker.educ.printing.model.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepo extends JpaRepository<Response, ResponseId> {
}
