package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepo extends JpaRepository<Message, UUID> {
    List<Message> findAllByChat(Chat chat);

}
