package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderRepo repo;

    @GetMapping
    public List<Order> getAllOrders(@RequestParam(required = false) String description) {

        List<Order> orders = new ArrayList<>();

        if (description == null)
            orders.addAll(repo.findAll());
        else
            orders.addAll(repo.findByDescriptionContaining(description));

        return orders;
    }

    @GetMapping("{id}")
    public Order getOrderById(@PathVariable("id") UUID id) {
        Optional<Order> orderData = repo.findById(id);

        if (orderData.isPresent()) {
            return orderData.get();
        } else {
            throw new NotFoundException();
        }
    }

    @PostMapping
    public Order createOrder(
            @RequestBody Order inputOrder,
            @AuthenticationPrincipal User user
    ) {

        Order order = new Order(
                user.getId(),
                inputOrder.getStatus(),
                inputOrder.getSum(),
                inputOrder.getHeight(),
                inputOrder.getWidth(),
                inputOrder.getLength(),
                inputOrder.getDescription()
        );

        order.setId(UUID.randomUUID());
        order.setDate(new Date());

        return repo.save(order);
    }

    @PutMapping("{id}")
    public Order updateOrder(
            @PathVariable("id") UUID id,
            @RequestBody Order inputOrder
    ) {
        Optional<Order> orderData = repo.findById(id);

        if (orderData.isPresent()) {
            Order order = orderData.get();
            order.setStatus(inputOrder.getStatus());
            order.setSum(inputOrder.getSum());
            order.setHeight(inputOrder.getHeight());
            order.setWidth(inputOrder.getWidth());
            order.setLength(inputOrder.getLength());
            order.setDescription(inputOrder.getDescription());
            return repo.save(order);
        } else {
            throw new NotFoundException();
        }
    }

    @DeleteMapping("{id}")
    public UUID deleteOrder(@PathVariable("id") UUID id) {

        repo.deleteById(id);

        return id;

    }
}
