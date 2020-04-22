package com.netcracker.educ.printing.model.repository;

import com.netcracker.educ.printing.model.bean.ChatId;
import com.netcracker.educ.printing.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<Chat, ChatId> {
}
