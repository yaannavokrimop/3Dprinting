package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.ResponseCreationException;
import com.netcracker.educ.printing.exception.NotFoundException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
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

            if (response!=null && !response.getStatus().equals(ResponseStatus.REFUSED)) {
                chatResponses.add(response);
            }
        }

        return chatResponses;
    }

    public Page<Response> getPageOfResponses(Map<String, String> params) {
        UUID orderId = UUID.fromString(params.get("orderId"));
        int currentPage = Integer.parseInt(params.get("page"))- 1;
        int perPage = Integer.parseInt(params.get("perPage"));
        Pageable page = PageRequest.of(currentPage, perPage , Sort.by("date").descending());
        return responseRepo.findAllByOrderId(orderId, page);
    }

    public ResponseRepresent responseToResponseRepresent(Response response) {
        return new ResponseRepresent(
                response.getExecutor().getId(),
                response.getSum(),
                "" + response.getExecutor().getSurname() + " " + response.getExecutor().getName(),
                response.getDate(),
                response.getStatus()
                );
    }

    public List<ResponseRepresent> responsesToResponseRepresents(List<Response> responses) {
        List<ResponseRepresent> represents = new ArrayList<>();
        for(Response response : responses) {
            represents.add(this.responseToResponseRepresent(response));
        }
        return represents;
    }
}
