package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.Message;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.MessageRepo;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MessageService {
    private MessageRepo messageRepo;
    private UserService userService;
    private ChatService chatService;

    public MessageService(MessageRepo messageRepo, UserService userService, ChatService chatService) {
        this.messageRepo = messageRepo;
        this.userService = userService;
        this.chatService = chatService;
    }

    public List<Message> getMessageByChat(UUID chatId,UserDetailsImpl principal){
        Chat chat=chatService.getChatById(chatId);
        if(chat!=null&&((chat.getCustomer().getId().equals(principal.getId()))||chat.getExecutor().getId().equals(principal.getId()))){
            return  messageRepo.findAllByChat(chat);
        }else return null;}



    public Message createMessage(Map<String,String> message){
        Message message1=new Message();
        message1.setDate(new Date());
        message1.setChat(chatService.getChatById(UUID.fromString(message.get("chatId"))));
        message1.setAuthor(userService.get(UUID.fromString(message.get("senderId"))));
        message1.setChecked(Boolean.FALSE);
            message1.setText(message.get("text"));
        return messageRepo.save(message1);
    }

    public void deleteMessage(Message message){
        messageRepo.deleteById(message.getId());
    }

    public void setChecked(Message message){
        messageRepo.findById(message.getId()).ifPresent(dbMessage -> dbMessage.setChecked(true));
    }
}
