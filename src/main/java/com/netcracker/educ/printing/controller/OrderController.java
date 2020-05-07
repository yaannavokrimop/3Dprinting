package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.bean.PaginationBean;
import com.netcracker.educ.printing.model.bean.ResponseStatus;
import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.Response;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.ChatRepo;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.ChatService;
import com.netcracker.educ.printing.service.OrderService;
import com.netcracker.educ.printing.service.ResponseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private final OrderRepo repo;
    private final OrderService orderService;
    private final ChatRepo chatRepo;
    private final ChatService chatService;
    private final ResponseService responseService;

    @GetMapping("/user")
    public ResponseEntity<PaginationBean> getOrdersByUserId(@RequestParam Map<String, String> pageParams, @AuthenticationPrincipal UserDetailsImpl principal) {
        Page<Order> ordersPage = orderService.getPageOfOrders(pageParams, principal.getId());
        List<OrderRepresent> orders = orderService.ordersToOrderRepresents(ordersPage.getContent());
        return ResponseEntity.ok(new PaginationBean(ordersPage.getTotalPages(), orders));
    }

    @GetMapping("{id}")
    public Order getOrderById(@PathVariable("id") UUID id) {

        log.info("get Order by id= {}",id);
        Optional<Order> orderData = repo.findById(id);

        if (orderData.isPresent()) {
            return orderData.get();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping("forchat/{chatId}")
    public List<Order> getOrdersForChat(@PathVariable(name = "chatId") UUID chatId) {
        List<Response> chatResponses = responseService.getResponsesForChat(chatId);

        List<Order> chatOrders = new ArrayList<>();

        for (Response response : chatResponses) {
            if (response.getStatus() == ResponseStatus.AGREED) {
                chatOrders.add(response.getOrder());
            }
        }

        return chatOrders;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRepresent inputOrder, @AuthenticationPrincipal UserDetailsImpl details) {
            return orderService.create(inputOrder,details.getId());
    }

    @PostMapping("draft")
    public Order createDraft(@RequestBody OrderRepresent inputOrder,@AuthenticationPrincipal UserDetailsImpl details) {
        return orderService.createDraft(inputOrder,details.getId());
    }

    @PutMapping("{id}")
    public Order updateOrder(
            @RequestBody Order inputOrder,
            @PathVariable("id") Order dbOrder
    ) {
        log.info("Order: "+inputOrder.toString()+";    dbOrder: "+dbOrder.toString());

        BeanUtils.copyProperties(inputOrder,dbOrder,"user", "materials");
        return repo.save(dbOrder);
    }

    @DeleteMapping("{id}")
    public UUID deleteOrder(@PathVariable("id") UUID id) {
        return orderService.deleteOrder(id);
    }

    @PatchMapping("/pay/{id}")
    public Order payOrder(@PathVariable("id") UUID id) {
        Order order = repo.findById(id).orElse(null);

        assert order != null;
        order.setStatus(OrderStatus.PAYED);

        repo.save(order);
        log.info("Order payed=" + order.getId());

        return order;
    }

    @PatchMapping("/done/{id}")
    public Order doneOrder(@PathVariable("id") UUID id) {
        Order order = repo.findById(id).orElse(null);

        assert order != null;
        order.setStatus(OrderStatus.DONE);

        repo.save(order);
        log.info("Order done=" + order.getId());

        return order;
    }

    @PatchMapping("/receive/{id}")
    public Order receivedOrder(@PathVariable("id") UUID id) {
        Order order = repo.findById(id).orElse(null);

        assert order != null;
        order.setStatus(OrderStatus.RECEIVED);

        repo.save(order);
        log.info("Order received=" + order.getId());

        return order;
    }
}
