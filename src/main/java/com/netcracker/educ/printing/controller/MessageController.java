package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.Message;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.ChatService;
import com.netcracker.educ.printing.service.MessageService;
import com.netcracker.educ.printing.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private MessageService messageService;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("{chatId}")
    public List<Message> getMessageByChat(@PathVariable(name = "chatId") UUID chatId, @AuthenticationPrincipal UserDetailsImpl principal) {
        return messageService.getMessageByChat(chatId,principal);
    }

    @PostMapping
    public Message createMessage(@RequestBody Map<String,String>message){
       return messageService.createMessage(message);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@AuthenticationPrincipal UserDetailsImpl principal,@PathVariable("id") UUID messageId){
    }

}



