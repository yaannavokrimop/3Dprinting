package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.CreatingResponseException;
import com.netcracker.educ.printing.model.bean.ResponseId;
import com.netcracker.educ.printing.model.bean.ResponseStatus;
import com.netcracker.educ.printing.model.entity.Response;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.ResponseRepo;
import com.netcracker.educ.printing.model.representationModel.ResponseRepresent;
import com.netcracker.educ.printing.service.ChatService;
import com.netcracker.educ.printing.service.ResponseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/response")
@AllArgsConstructor
@Data
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
        return responseService.getResponsesForChat(chatId);
    }

    @PostMapping("/offer")
    public void makeAnOffer(@RequestBody ResponseRepresent responseRepresent) {
        responseService.makeAnOffer(responseRepresent);
    }

    @PatchMapping("/offer")
    public void refuseResponse(@RequestBody ResponseRepresent responseRepresent) {
        responseService.refuseResponse(responseRepresent);
    }

    @PatchMapping("/offer/discuss")
    public void refuseOffer(@RequestBody ResponseRepresent responseRepresent) {
        responseService.refuseOffer(responseRepresent);
    }

    @PatchMapping("/offer/accept")
    public void acceptOffer(@RequestBody ResponseRepresent responseRepresent) {
        responseService.acceptOffer(responseRepresent);
    }
}
