package com.netcracker.educ.printing.controller;

import com.fasterxml.jackson.databind.node.BooleanNode;
import com.netcracker.educ.printing.exception.CreatingResponseException;
import com.netcracker.educ.printing.model.bean.OrderStatus;
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
import java.util.Map;
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
        log.info("/////////////////////////////////////////////////Responses for chat id=" + chatId);
        List<Response> responsesForChat =  responseService.getResponsesForChat(chatId);
        return responsesForChat;
    }

    @PostMapping("/offer")
    public void makeAnOffer(@RequestBody ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElse(null);

        if (dbResponse != null) {
            dbResponse.setSum(responseRepresent.getSum());

            if (responseRepresent.isExecutor()) {
                dbResponse.setStatus(ResponseStatus.BY_EXECUTOR);
            } else {
                dbResponse.setStatus(ResponseStatus.BY_CUSTOMER);
            }

            responseRepo.save(dbResponse);
            log.info("/////////////////////////////////////////////////Successful offer=" + responseRepresent.getSum());

        }
    }

    @PatchMapping("/offer")
    public void refuseResponse(@RequestBody ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElse(null);

        assert dbResponse != null;
        dbResponse.setStatus(ResponseStatus.REFUSED);

        responseRepo.save(dbResponse);
        log.info("/////////////////////////////////////////////////Response Refused=" + dbResponse.getSum());
    }
}
