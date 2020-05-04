package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.MaterialType;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.entity.Material;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.ResponseRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final MaterialRepo materialRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final ResponseRepo responseRepo;
    private final MaterialService materialService;

    public Order create(OrderRepresent represent,UUID userId) throws RuntimeException {
        represent.setId(UUID.randomUUID());
        Order order = new Order(represent,OrderStatus.NO_PAY,new Date(),materialsFromList(represent.getMaterials()),userRepo.findById(userId).orElseThrow(NotFoundException::new));
        Order dbOrder=orderRepo.save(order);
        log.info("User {} created order {}",userId,order.getId());
        return dbOrder;
    }

    public Page<Order> getPageOfOrders(Map<String, String> pageParams, UUID principalId) {
        int currentPage = Integer.parseInt(pageParams.get("page"))- 1;
        int perPage = Integer.parseInt(pageParams.get("perPage"));
        String name = pageParams.get("orderName");
        Pageable page = PageRequest.of(currentPage, perPage , Sort.by("date").descending());
        if ( name == null || name.isEmpty()|| name.equals("null")) return orderRepo.findAllByUserId(principalId, page);
        else return orderRepo.findAllByNameContainingAndUserId(name, principalId, page);
    }

    public Order createDraft(OrderRepresent inputOrder, UUID userId) {
        User user = userRepo.findById(userId).orElseThrow(NotFoundException::new);
        inputOrder.setId(UUID.randomUUID());
        Order order=new Order(inputOrder,OrderStatus.DRAFT,new Date(),materialsFromList(inputOrder.getMaterials()),user);
        Order dbOrder=orderRepo.save(order);
        log.info("User {} created orderDraft {}",userId,order.getId());
        return dbOrder;
    }

    public UUID deleteOrder(UUID orderId) {
        orderRepo.deleteById(orderId);
        log.info("Order {}  was deleted",orderId);
        return orderId;
    }

    public Set<Material> materialsFromList(List<String> materialTitleList){
        Set<Material> materials = new HashSet<>();
        for(String materialName:materialTitleList) {
            materials.add(materialRepo.findByMatTitle(materialName));
        }
        return materials;
    }

    public OrderRepresent orderToOrderRepresent(Order order) {
        return new OrderRepresent(
                order.getId(),
                order.getSum(),
                order.getName(),
                order.getDescription(),
                order.getHeight(),
                order.getWidth(),
                order.getWidth(),
                materialService.MaterialSetToMatTitleList(order.getMaterials()),
                responseRepo.countDistinctByOrderId(order.getId()),
                order.getStatus(),
                order.getFile(),
                order.getUser().getId(),
                order.getDate()
                );
    }

    public List<OrderRepresent> ordersToOrderRepresents(List<Order> orders) {
        List<OrderRepresent> orderRepresents = new ArrayList<>();
        for (Order order : orders) {
            orderRepresents.add(this.orderToOrderRepresent(order));
        }
        return orderRepresents;
    }
}
