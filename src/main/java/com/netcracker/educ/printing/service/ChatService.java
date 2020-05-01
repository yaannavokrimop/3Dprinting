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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepo chatRepo;
    private final UserRepo userRepo;

    public void createChat(ChatRepresent represent) throws RuntimeException {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getId().equals(represent.getExecutorId()))
            throw new ResponseCreationException("Нельзя создать чат с самим собой!");
        User executor = userRepo.findById(represent.getExecutorId())
                .orElseThrow(NotFoundException::new);
        User customer = userRepo.findById(represent.getCustomerId())
                .orElseThrow(NotFoundException::new);
        if (chatRepo.existsByExecutorAndCustomer(executor, customer) || chatRepo.existsByExecutorAndCustomer(customer, executor))
            throw new ResponseCreationException("Этот чат уже есть!");
        chatRepo.save(new Chat(executor, customer));

    }

    public List<ChatRepresent> chatToChatRepresent (List<Chat> chats) {
        List<ChatRepresent> chatRepresents = new ArrayList<>();
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepo.findByEmail(principal.getEmail());

        for (Chat chat : chats) {
            User companion;
            Boolean isExecutor;

            if (currentUser.getId().equals(chat.getCustomer().getId())) {
                companion = chat.getExecutor();
                isExecutor = false;
            } else {
                companion = chat.getCustomer();
                isExecutor = true;
            }

            chatRepresents.add(new ChatRepresent(
                    chat.getId(),
                    chat.getExecutor().getId(),
                    chat.getCustomer().getId(),
                    companion.getName() + " " + companion.getSurname(),
                    isExecutor
                    )
            );
        }

        return chatRepresents;
    }

    public Chat getChatById(UUID chatId) {
        Optional<Chat> chatOptional= chatRepo.findById(chatId);
        return chatOptional.orElse(null);
    }
}
