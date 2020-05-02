package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.MaterialType;
import com.netcracker.educ.printing.model.entity.Material;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
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
public class OrderService {
    private final MaterialRepo materialRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;

    public Order create(OrderRepresent represent) throws RuntimeException {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MaterialType materialType = MaterialType.valueOf(represent.getMaterial());
        Material material = materialRepo.findByType(materialType);
        Set<Material> materials = new HashSet<>();
        materials.add(material);

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
        order.setUser(userRepo.findByEmail(principal.getEmail()));
        order.setDate(new Date());
        order.setMaterials(materials);

        return order;
    }

    public Page<Order> getPageOfOrders(Map<String, String> pageParams) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int currentPage = Integer.parseInt(pageParams.get("page"))- 1;
        int perPage = Integer.parseInt(pageParams.get("perPage"));
        Pageable page = PageRequest.of(currentPage, perPage , Sort.by("date"));
        return orderRepo.findAllByUserId(principal.getId(), page);
    }
}
