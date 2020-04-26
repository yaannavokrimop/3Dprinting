package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.CreatingResponseException;
import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.Response;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.ResponseRepo;
import com.netcracker.educ.printing.model.representationModel.ResponseRepresent;
import com.netcracker.educ.printing.service.ChatService;
import com.netcracker.educ.printing.service.ResponseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/response")
@AllArgsConstructor
public class ResponseController {
    private final ResponseService responseService;
    private final ChatService chatService;
    private final OrderController orderController;
    private final OrderRepo orderRepo;
    private final ResponseRepo responseRepo;

    @PostMapping
    public ResponseEntity<String> createResponse(@RequestBody ResponseRepresent responseRepresent) {
        try {
            responseService.createResponse(responseRepresent);
        } catch (CreatingResponseException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("Заказ успешно отправлен исполнителю.");
    }

    @GetMapping("forchat/{chatId}")
    public List<Response> getResponsesForChat(@PathVariable(name = "chatId") UUID chatId) {
        log.info("/////////////////////////////////////////////////Responses for chat id=" + chatId);
        return responseService.getResponsesForChat(chatId);
    }
}
