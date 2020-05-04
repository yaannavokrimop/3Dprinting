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

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/api/message")
public class MessageController {

    private MessageService messageService;
    private UserRepo userRepo;

    @GetMapping("{chatId}")
    public List<MessageRepresent> getMessageByChat(@PathVariable(name = "chatId") UUID chatId, @AuthenticationPrincipal UserDetailsImpl principal) {

        List<Message> messageList = messageService.getMessageByChat(chatId, principal);
        List<MessageRepresent> messageRepresents = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        for (Message message : messageList) {
            MessageRepresent represent = new MessageRepresent(message.getText(), message.getChat().getId());
            represent.setAuthor(message.getAuthor().getName()+" "+message.getAuthor().getSurname());
            represent.setAuthorId(message.getAuthor().getId());
            represent.setDate(format.format(message.getDate()));
            messageRepresents.add(represent);
        }

        return messageRepresents;
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



