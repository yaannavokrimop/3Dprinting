package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.MaterialType;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.entity.Material;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.OrderRepo;
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

    public Order create(OrderRepresent represent,UUID userId) throws RuntimeException {
        Order order = new Order(
                represent.getSum(),
                represent.getHeight(),
                represent.getWidth(),
                represent.getLength(),
                represent.getName()
        );

        if (represent.getDescription() != null) {
            order.setDescription(represent.getDescription());
        }

        order.setId(UUID.randomUUID());
        order.setUser(userRepo.findById(userId).orElse(null));
        order.setDate(new Date());
        order.setMaterials(materialsFromList(represent.getMaterial()));
        order.setStatus(OrderStatus.NO_PAY);
        log.info("User "+userId+" created order "+order.getId());
        return orderRepo.save(order);
    }

    public Page<Order> getPageOfOrders(Map<String, String> pageParams) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int currentPage = Integer.parseInt(pageParams.get("page"))- 1;
        int perPage = Integer.parseInt(pageParams.get("perPage"));
        Pageable page = PageRequest.of(currentPage, perPage , Sort.by("date"));
        return orderRepo.findAllByUserId(principal.getId(), page);
    }

    public Order createDraft(OrderRepresent inputOrder, UUID userId) {
        User user = userRepo.findById(userId).orElse(null);
       Order order= new Order(
               inputOrder.getSum(),
               inputOrder.getHeight(),
               inputOrder.getWidth(),
               inputOrder.getLength(),
               inputOrder.getDescription());
        order.setUser(user);
        order.setMaterials(materialsFromList(inputOrder.getMaterial()));
        order.setId(UUID.randomUUID());
        order.setStatus(OrderStatus.DRAFT);
        order.setDate(new Date());
        log.info("User "+userId+" created orderDraft "+order.getId());
        return orderRepo.save(order);

    }

    public UUID deleteOrder(UUID orderId) {
        orderRepo.deleteById(orderId);
        log.info("Order "+orderId+"  was deleted");
        return orderId;
    }

    public Set<Material> materialsFromList(List<String> materialTitleList){
        Set<Material> materials = new HashSet<>();
        for(String materialName:materialTitleList) {
            materials.add(materialRepo.findByMatTitle(materialName));
        }
        return materials;
    }
}
