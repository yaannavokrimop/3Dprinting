package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.MaterialType;
import com.netcracker.educ.printing.model.entity.Material;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final MaterialRepo materialRepo;
    private final UserRepo userRepo;

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
                represent.getDescription()
        );

        order.setId(UUID.randomUUID());
        order.setUser(userRepo.findByEmail(principal.getEmail()));
        order.setDate(new Date());
        order.setMaterials(materials);

        return order;
    }
}
