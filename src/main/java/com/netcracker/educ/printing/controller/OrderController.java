package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.PaginationBean;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private final OrderRepo orderRepo;
    private final OrderService orderService;

    @GetMapping("/user")
    public ResponseEntity<PaginationBean> getOrdersByUserId(@RequestParam Map<String, String> pageParams, @AuthenticationPrincipal UserDetailsImpl principal) {
        Page<Order> ordersPage = orderService.getPageOfOrders(pageParams, principal.getId());
        List<OrderRepresent> orders = orderService.ordersToOrderRepresents(ordersPage.getContent());

        return ResponseEntity.ok(new PaginationBean(ordersPage.getTotalPages(), orders));
    }

    @GetMapping("{id}")
    public OrderRepresent getOrderById(@PathVariable("id") UUID id) {
        log.info("get Order by id= {}", id);
        return orderService.getOrderById(id);
    }

    @GetMapping("forchat/{chatId}")
    public List<Order> getOrdersForChat(@PathVariable(name = "chatId") UUID chatId) {
        return orderService.getOrdersForChat(chatId);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRepresent inputOrder, @AuthenticationPrincipal UserDetailsImpl details) {
        return orderService.create(inputOrder, details.getId());
    }

    @PostMapping("draft")
    public Order createDraft(@RequestBody OrderRepresent inputOrder, @AuthenticationPrincipal UserDetailsImpl details) {
        return orderService.createDraft(inputOrder, details.getId());
    }

    @PutMapping("{id}")
    public Order updateOrder(
            @RequestBody OrderRepresent inputOrder,
            @PathVariable("id") UUID orderId) {
        return orderService.updateOrder(inputOrder,orderId);
    }

    @DeleteMapping("{id}")
    public UUID deleteOrder(@PathVariable("id") UUID id) {
        return orderService.deleteOrder(id);
    }

    @PatchMapping("/pay/{id}")
    public Order payOrder(@PathVariable("id") UUID id) {
        return orderService.payOrder(id);
    }

    @PatchMapping("/done/{id}")
    public Order doneOrder(@PathVariable("id") UUID id) {
        return orderService.doneOrder(id);
    }

    @PatchMapping("/receive/{id}")
    public Order receivedOrder(@PathVariable("id") UUID id) {
        return orderService.receivedOrder(id);
    }

    @PatchMapping("/notDraft/{id}")
    public Order notDraft(@PathVariable("id") UUID id) {
        return orderService.notDraftOrder(id);
    }
}
