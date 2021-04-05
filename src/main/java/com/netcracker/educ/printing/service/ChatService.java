package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.ResponseCreationException;
import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.ChatRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.ChatRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepo chatRepo;
    private final UserRepo userRepo;

    public void createChat(ChatRepresent represent) throws RuntimeException {
        log.info("Create chat Executor: {} and Customer: {}",represent.getExecutorId(),represent.getCustomerId());
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getId().equals(represent.getExecutorId())){
            log.error("ResponseCreationException, User: {}",principal.getId());
            throw new ResponseCreationException("Нельзя создать чат с самим собой!");}
        User executor = userRepo.findById(represent.getExecutorId())
                .orElseThrow(NotFoundException::new);
        User customer = userRepo.findById(represent.getCustomerId())
                .orElseThrow(NotFoundException::new);
        if (chatRepo.existsByExecutorAndCustomer(executor, customer))
            throw new ResponseCreationException("Этот чат уже есть!");
        chatRepo.save(new Chat(executor, customer));
        log.info("Chat Executor: {} and Customer {} created",executor.getId(),customer.getId());
    }

    public List<ChatRepresent> chatsToChatRepresents(List<Chat> chats) {
        List<ChatRepresent> chatRepresents = new ArrayList<>();
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("Chats to chatRepresents, Current user: {}",principal.getId());
        User currentUser = userRepo.findByEmail(principal.getEmail());
        for (Chat chat : chats) {
            chatRepresents.add(chatToChatRepresent(chat, currentUser));
        }
        log.info("Get chatRepresents from chats for user {}",principal.getId());
        return chatRepresents;
    }

    public ChatRepresent chatToChatRepresent(Chat chat, User currentUser) {
        log.debug("Chats to chatRepresents, Current user: {}",currentUser.getId());
        User companion;
        Boolean isExecutor;
        if (currentUser.getId().equals(chat.getCustomer().getId())) {
            companion = chat.getExecutor();
            isExecutor = false;
        } else {
            companion = chat.getCustomer();
            isExecutor = true;
        }
        log.info("Get chatRepresent from chat for user {}",currentUser.getId());
        return new ChatRepresent(
                        chat.getId(),
                        chat.getExecutor().getId(),
                        chat.getCustomer().getId(),
                        companion.getName() + " " + companion.getSurname(),
                        isExecutor
                );
    }

    public Chat getChatById(UUID chatId) {
        Optional<Chat> chatOptional= chatRepo.findById(chatId);
        log.info("Get chat by chatId= {}",chatId);
        return chatOptional.orElse(null);
    }

    public Chat getChatByExecutorIdAndCustomerId(UUID executorId, UUID customerId) {
        Chat chat=chatRepo.findByExecutorIdAndCustomerId(executorId, customerId).orElseThrow(NotFoundException::new);
        log.info("Get chat by executorId= {} and customerId= {}",executorId,customerId);
        return chat;
    }
}
