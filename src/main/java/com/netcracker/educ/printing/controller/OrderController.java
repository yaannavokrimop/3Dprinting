package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.bean.PaginationBean;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.OrderService;
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

    private OrderRepo repo;
    private OrderService orderService;




    @GetMapping("/user")
    public ResponseEntity<PaginationBean> getOrdersByUserId(@RequestParam Map<String, String> pageParams) {
        Page<Order> orders = orderService.getPageOfOrders(pageParams);
        return ResponseEntity.ok(new PaginationBean(orders.getTotalPages(), orders.getContent()));
    }

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

        log.info("get Order by id="+id);
        Optional<Order> orderData = repo.findById(id);

        if (orderData.isPresent()) {
            return orderData.get();
        } else {
            throw new NotFoundException();
        }
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
}
