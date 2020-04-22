package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.CreatingResponseException;
import com.netcracker.educ.printing.model.representationModel.ChatRepresent;
import com.netcracker.educ.printing.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;

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
