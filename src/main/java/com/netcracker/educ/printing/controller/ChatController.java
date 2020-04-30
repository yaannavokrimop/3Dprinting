package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.CreatingResponseException;
import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.ChatRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.ChatRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@Data
@AllArgsConstructor
public class ChatController {
    private final UserRepo userRepo;
    private final ChatService chatService;
    private final ChatRepo chatRepo;

    @GetMapping
    public List<ChatRepresent> getMyChats() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepo.findByEmail(principal.getEmail());
        List<Chat> myChats = chatRepo.findAllByExecutorOrCustomer(currentUser, currentUser);
        return chatService.chatToChatRepresent(myChats);
    }

    @GetMapping("{id}")
    public Chat getChat(@PathVariable("id")UUID id) {
        log.info("/////////////////////////////////////////////////Chat  id=" + id);
        return chatService.getChatById(id);
    }

    @PostMapping
    public ResponseEntity<String> createChat(@RequestBody ChatRepresent chatRepresent) {
        try {
            chatService.createChat(chatRepresent);
        } catch (CreatingResponseException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("Чат успешно создан.");
    }
}
