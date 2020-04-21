package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.entity.Equipment;
import com.netcracker.educ.printing.model.entity.ExecutorEquipment;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.EquipmentRepo;
import com.netcracker.educ.printing.model.repository.ExecutorEquipmentRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class EquipmentService {
    private final EquipmentRepo equipmentRepo;
    private final UserRepo userRepo;
    private final ExecutorEquipmentRepo executorEquipmentRepo;

    public Equipment create(String userEmail, Equipment equipment) {
        equipmentRepo.save(equipment);
        User executor = userRepo.findByEmail(userEmail);
        ExecutorEquipment executorEquipment = new ExecutorEquipment(executor, equipment);
        executorEquipmentRepo.save(executorEquipment);
        return equipment;
    }

    public List<Equipment> getUserEquipment(UUID userId) {
        return executorEquipmentRepo.findAllByExecutorId(userId).stream()
                .map(ExecutorEquipment::getEquipment)
                .collect(Collectors.toList());
    }

    public List<String> getEquipmentsByEquipNamePart(String equipName) {
        return equipmentRepo.findEquipNameByEquipNameContaining(equipName);
    }

    public Equipment getEquipmentByName(String equipName) {
      return equipmentRepo.findByEquipName(equipName);
    }
}
