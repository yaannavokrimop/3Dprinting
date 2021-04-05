package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.Message;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.MessageRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.ChatService;
import com.netcracker.educ.printing.service.MessageService;
import com.netcracker.educ.printing.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/api/message")
public class MessageController {

    private MessageService messageService;
    private UserRepo userRepo;

    @GetMapping("{chatId}")
    public List<Message> getMessageByChat(@PathVariable(name = "chatId") UUID chatId, @AuthenticationPrincipal UserDetailsImpl principal) {
        return messageService.getMessageByChat(chatId, principal);
    }

    @PostMapping
    public Message createMessage(@AuthenticationPrincipal UserDetailsImpl principal, @RequestBody MessageRepresent message) {
        User currentUser = userRepo.findByEmail(principal.getEmail());
        return messageService.createMessage(message, currentUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@AuthenticationPrincipal UserDetailsImpl principal, @PathVariable("id") UUID messageId) {
    }

}



