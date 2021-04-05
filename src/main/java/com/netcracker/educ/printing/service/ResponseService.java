package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.CreatingResponseException;
import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.bean.ResponseId;
import com.netcracker.educ.printing.model.bean.ResponseStatus;
import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.Response;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.ResponseRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.ResponseRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ResponseService {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final ResponseRepo responseRepo;
    private final ChatService chatService;

    public void createResponse(ResponseRepresent represent) throws CreatingResponseException {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getId().equals(represent.getExecutorId()))
            throw new CreatingResponseException("Нельзя принять собственный заказ. Выберите другого исполнителя!");
        User executor = userRepo.findById(represent.getExecutorId())
                .orElseThrow(NotFoundException::new);
        Order order = orderRepo.findById(represent.getOrderId())
                .orElseThrow(NotFoundException::new);
        ResponseId responseId = new ResponseId(represent.getOrderId(), represent.getExecutorId());
        if (responseRepo.existsById(responseId))
            throw new CreatingResponseException("Этот пользователь уже выбран исполнителем текущего заказа!");
        responseRepo.save(new Response(responseId, order, executor, ResponseStatus.REQUESTED, represent.getSum(), new Date()));
    }

    public List<Response> getResponsesForChat(UUID chatId) {
        Chat currentChat = chatService.getChatById(chatId);

        User customer = currentChat.getCustomer();
        User executor = currentChat.getExecutor();

        List<Order> customerOrders = orderRepo.findByUserId(customer.getId());
        List<Response> chatResponses = new ArrayList<>();

        for (Order customerOrder : customerOrders) {
            Optional<Response> responseOptional = Optional.ofNullable(responseRepo.findByOrderAndExecutor(customerOrder, executor));
            Response response = responseOptional.orElse(null);

            if (response != null && !response.getStatus().equals(ResponseStatus.REFUSED) ) {
                chatResponses.add(response);
            }
        }
        log.info("Responses for chat id=" + chatId);

        return chatResponses;
    }

    public void makeAnOffer(ResponseRepresent responseRepresent) {
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
            log.info("Successful offer=" + responseRepresent.getSum());
        }
    }

    public void refuseResponse(ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElse(null);

        assert dbResponse != null;
        dbResponse.setStatus(ResponseStatus.REFUSED);

        responseRepo.save(dbResponse);
        log.info("Response Refused=" + dbResponse.getSum());
    }

    public void refuseOffer(ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElse(null);

        assert dbResponse != null;
        dbResponse.setStatus(ResponseStatus.DISCUSSION);

        responseRepo.save(dbResponse);
        log.info("Response Discuss=" + dbResponse.getSum());
    }

    public void acceptOffer(@RequestBody ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElse(null);

        assert dbResponse != null;
        List<Response> thisOrderResponses = responseRepo.findAllByOrder(dbResponse.getOrder());

        for (Response response : thisOrderResponses) {
            response.setStatus(ResponseStatus.REFUSED);
            responseRepo.save(response);
        }

        dbResponse.setStatus(ResponseStatus.AGREED);
        responseRepo.save(dbResponse);

        Order currentOrder = dbResponse.getOrder();
        currentOrder.setStatus(OrderStatus.NO_PAY);
        orderRepo.save(currentOrder);

        log.info("Response agreed=" + dbResponse.getSum());
    }
}
