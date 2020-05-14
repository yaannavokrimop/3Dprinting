package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.ResponseCreationException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ResponseService {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final ResponseRepo responseRepo;
    private final ChatService chatService;

    public void createResponse(ResponseRepresent represent) throws ResponseCreationException {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getId().equals(represent.getExecutorId()))
            throw new ResponseCreationException("Нельзя принять собственный заказ. Выберите другого исполнителя!");
        User executor = userRepo.findById(represent.getExecutorId())
                .orElseThrow(NotFoundException::new);
        Order order = orderRepo.findById(represent.getOrderId())
                .orElseThrow(NotFoundException::new);
        ResponseId responseId = new ResponseId(represent.getOrderId(), represent.getExecutorId());
        if (responseRepo.existsById(responseId))
            throw new ResponseCreationException("Этот пользователь уже выбран исполнителем текущего заказа!");
        responseRepo.save(new Response(responseId, order, executor, ResponseStatus.REQUESTED, represent.getSum(), new Date()));
        log.info("Response to order {} was created",represent.getOrderId());
    }

    public List<Response> getResponsesForChat(UUID chatId) {
        Chat currentChat = chatService.getChatById(chatId);

        User customer = currentChat.getCustomer();
        User executor = currentChat.getExecutor();

        List<Order> customerOrders = orderRepo.findByUserId(customer.getId());
        List<Response> chatResponses = new ArrayList<>();

        for (Order customerOrder : customerOrders) {
            Optional<Response> responseOptional = Optional.ofNullable(responseRepo.findByOrderAndExecutor(customerOrder, executor));
            responseOptional.filter(i -> !ResponseStatus.REFUSED.equals(i.getStatus())).ifPresent(chatResponses::add);
        }
        log.info("Responses for chat id={}", chatId);

        return chatResponses;
    }

    public void makeAnOffer(ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElseThrow(NotFoundException::new);

        if (dbResponse.getStatus() == ResponseStatus.REQUESTED || dbResponse.getStatus() == ResponseStatus.DISCUSSION) {
            dbResponse.setSum(responseRepresent.getSum());

            if (responseRepresent.isExecutor()) {
                dbResponse.setStatus(ResponseStatus.BY_EXECUTOR);
            } else {
                dbResponse.setStatus(ResponseStatus.BY_CUSTOMER);
            }

            responseRepo.save(dbResponse);
            log.info("Successful offer={}", responseRepresent.getSum());
        }
    }

    public void refuseResponse(ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElseThrow(NotFoundException::new);

        dbResponse.setStatus(ResponseStatus.REFUSED);

        responseRepo.save(dbResponse);
        log.info("Response Refused={}", dbResponse.getSum());
    }

    public void refuseOffer(ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElseThrow(NotFoundException::new);

        if (dbResponse.getStatus() == ResponseStatus.BY_CUSTOMER || dbResponse.getStatus() == ResponseStatus.BY_EXECUTOR) {
            dbResponse.setStatus(ResponseStatus.DISCUSSION);

            responseRepo.save(dbResponse);
            log.info("Response Discuss={}", dbResponse.getSum());
        }
    }

    public void acceptOffer(ResponseRepresent responseRepresent) {
        Response dbResponse = responseRepo.findById(new ResponseId(
                responseRepresent.getOrderId(),
                responseRepresent.getExecutorId()
        )).orElseThrow(NotFoundException::new);

        if (dbResponse.getStatus() == ResponseStatus.BY_CUSTOMER || dbResponse.getStatus() == ResponseStatus.BY_EXECUTOR) {
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

            log.info("Response agreed={}", dbResponse.getSum());
        }
    }

    public Page<Response> getPageOfResponsesForCustomer(Map<String, String> params) {
        UUID orderId = UUID.fromString(params.get("orderId"));
        int currentPage = Integer.parseInt(params.get("page")) - 1;
        int perPage = Integer.parseInt(params.get("perPage"));
        Pageable page = PageRequest.of(currentPage, perPage, Sort.by("date").descending());
        Page<Response> pages= responseRepo.findAllByOrderId(orderId, page);
        log.info("Get page of responses for order: {}",orderId);
        return pages;
    }

    public Page<Response> getPageOfResponsesForExecutor(Map<String, String> params, UUID execId) {
        int currentPage = Integer.parseInt(params.get("page")) - 1;
        int perPage = Integer.parseInt(params.get("perPage"));
        Pageable page = PageRequest.of(currentPage, perPage, Sort.by("date").descending());
        Page<Response> pages= responseRepo.findAllByExecutorId(execId, page);
        log.info("Get page of responses for executor: {}",execId);
        return pages;
    }

    public ResponseRepresent responseToResponseRepresent(Response response) {
        ResponseRepresent responseRepresent = new ResponseRepresent();
        responseRepresent.setExecutorId(response.getExecutor().getId());
        responseRepresent.setSum(response.getSum());
        responseRepresent.setExecutorInfo("" + response.getExecutor().getSurname() + " " + response.getExecutor().getName());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        responseRepresent.setDate(format.format(response.getDate()));
        responseRepresent.setStatus(statusToString(response.getStatus()));
        responseRepresent.setCustomerId(response.getOrder().getUser().getId());
        responseRepresent.setCustomerInfo("" + response.getOrder().getUser().getSurname() + " " + response.getOrder().getUser().getName());
        responseRepresent.setOrderName(response.getOrder().getName());
        responseRepresent.setOrderId(response.getOrder().getId());
        return responseRepresent;
    }

    public List<ResponseRepresent> responsesToResponseRepresents(List<Response> responses) {
        List<ResponseRepresent> represents = new ArrayList<>();
        for (Response response : responses) {
            represents.add(this.responseToResponseRepresent(response));
        }
        return represents;
    }

    public String statusToString(ResponseStatus status) {
        switch (status) {
            case REQUESTED :
                return "Запрошено";
            case DISCUSSION:
                return "В обсуждении";
            case BY_CUSTOMER :
                return "Цена предложена заказчиком";
            case BY_EXECUTOR :
                return "Цена предложена исполнителем";
            case AGREED :
                return "Заказ согласован";
            case REFUSED :
                return "Заказ отклонен";
        }
        return "Статус не найден";
    }
}
