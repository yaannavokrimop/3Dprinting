package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.ResponseCreationException;
import com.netcracker.educ.printing.model.bean.PaginationBean;
import com.netcracker.educ.printing.model.entity.Response;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.ResponseRepo;
import com.netcracker.educ.printing.model.representationModel.ResponseRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.ChatService;
import com.netcracker.educ.printing.service.ResponseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        log.debug("User {} create response for order {}",responseRepresent.getExecutorId(),responseRepresent.getOrderId());
        try {
            responseService.createResponse(responseRepresent);
        } catch (ResponseCreationException ex) {
            log.error("ResponseCreationException {}",ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("Заказ успешно отправлен исполнителю.");
    }

    @GetMapping("forchat/{chatId}")
    public List<Response> getResponsesForChat(@PathVariable(name = "chatId") UUID chatId) {
        log.debug("Get responses for chat {}",chatId);
        return responseService.getResponsesForChat(chatId);
    }

    @PostMapping("/offer")
    public void makeAnOffer(@RequestBody ResponseRepresent responseRepresent) {
        log.debug("User {} make an offer for order {}",responseRepresent.getExecutorId(),responseRepresent.getOrderId());
        responseService.makeAnOffer(responseRepresent);
    }

    @PatchMapping("/offer")
    public void refuseResponse(@RequestBody ResponseRepresent responseRepresent) {
        log.debug("Refuse response from {} for order {}",responseRepresent.getExecutorId(),responseRepresent.getOrderId());
        responseService.refuseResponse(responseRepresent);
    }

    @PatchMapping("/offer/discuss")
    public void refuseOffer(@RequestBody ResponseRepresent responseRepresent) {
        log.debug("Refuse offer in order {}",responseRepresent.getOrderId());
        responseService.refuseOffer(responseRepresent);
    }

    @PatchMapping("/offer/accept")
    public void acceptOffer(@RequestBody ResponseRepresent responseRepresent) {
        log.debug("Accept offer in order {}",responseRepresent.getOrderId());
        responseService.acceptOffer(responseRepresent);
    }

    @GetMapping("/forcustomer")
    public ResponseEntity<PaginationBean> getResponsesForCustomer(@RequestParam Map<String, String> params) {
        log.debug("Get responses for customer");
        Page<Response> responsesPage = responseService.getPageOfResponsesForCustomer(params);
        List<ResponseRepresent> responses = responseService.responsesToResponseRepresents(responsesPage.getContent());
        return ResponseEntity.ok(new PaginationBean(responsesPage.getTotalPages(), responses));
    }

    @GetMapping("/forexecutor")
    public ResponseEntity<PaginationBean> getResponsesForExecutor(@RequestParam Map<String, String> params, @AuthenticationPrincipal UserDetailsImpl principal) {
        log.debug("Get responses for executor {}",principal.getId());
        Page<Response> responsesPage = responseService.getPageOfResponsesForExecutor(params, principal.getId());
        List<ResponseRepresent> responses = responseService.responsesToResponseRepresents(responsesPage.getContent());
        return ResponseEntity.ok(new PaginationBean(responsesPage.getTotalPages(), responses));
    }
}
