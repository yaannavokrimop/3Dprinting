package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderRepo repo;
    private UserRepo userRepo;

    @Autowired
    public OrderController(UserRepo userRepo,OrderRepo repo) {
        this.userRepo = userRepo;
        this.repo=repo;
    }


    @GetMapping("/user")
    public List<Order> getOrderByUserId() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByName(principal.getUsername());
        return repo.findByUserId(user.getId());

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

        log.info("/////////////////////////////////////////////////OrderController  id="+id);
        Optional<Order> orderData = repo.findById(id);

        if (orderData.isPresent()) {
            return orderData.get();
        } else {
            throw new NotFoundException();
        }
    }

    @PostMapping
    public Order createOrder(
            @RequestBody Order inputOrder
    ) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(principal.getEmail());

        inputOrder.setUserId(user.getId());
        inputOrder.setId(UUID.randomUUID());
        inputOrder.setStatus(OrderStatus.NO_PAY);
        inputOrder.setDate(new Date());

        return repo.save(inputOrder);
    }

    @PostMapping("draft")
    public Order createDraft(
            @RequestBody Order inputOrder
    ) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(principal.getEmail());

        inputOrder.setUserId(user.getId());
        inputOrder.setId(UUID.randomUUID());
        inputOrder.setStatus(OrderStatus.DRAFT);
        inputOrder.setDate(new Date());

        return repo.save(inputOrder);
    }

    @PutMapping("{id}")
    public Order updateOrder(
            @RequestBody Order inputOrder,
            @PathVariable("id") Order dbOrder
    ) {
        log.info("User: "+inputOrder.toString()+";    dbUser: "+dbOrder.toString());

        BeanUtils.copyProperties(inputOrder,dbOrder,"id");
        return repo.save(dbOrder);
    }

    @DeleteMapping("{id}")
    public UUID deleteOrder(@PathVariable("id") UUID id) {

        repo.deleteById(id);

        return id;

    }
}
