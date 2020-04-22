package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.entity.Equipment;
import com.netcracker.educ.printing.model.entity.ExecutorEquipment;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.EquipmentRepo;
import com.netcracker.educ.printing.model.repository.ExecutorEquipmentRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.EquipmentRepresent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class EquipmentService {
    private final EquipmentRepo equipmentRepo;
    private final UserRepo userRepo;
    private final ExecutorEquipmentRepo executorEquipmentRepo;

    public Equipment create(String userEmail, Equipment equipment,String equipDesc) {
        equipmentRepo.save(equipment);
        User executor = userRepo.findByEmail(userEmail);
        ExecutorEquipment executorEquipment = new ExecutorEquipment(executor, equipment,equipDesc);
        executorEquipmentRepo.save(executorEquipment);
        return equipment;
    }

    public List<EquipmentRepresent> getUserEquipment(UUID userId) {
        List<Equipment>equipmentList= executorEquipmentRepo.findAllByExecutorId(userId).stream()
                .map(ExecutorEquipment::getEquipment)
                .collect(Collectors.toList());
        return equipmentToEquipmentRepresent(equipmentList,userId);
    }

    public List<String> getEquipmentsByEquipNamePart(String equipName) {
        return equipmentRepo.findEquipNameByEquipNameContaining(equipName);
    }

    public Equipment getEquipmentByName(String equipName) {
      return equipmentRepo.findByEquipName(equipName);
    }

    public ExecutorEquipment addEquipment(String email, String equipName, String equipDesc) {


            Equipment equipment = equipmentRepo.findByEquipName(equipName);
            return executorEquipmentRepo.save(new ExecutorEquipment(userRepo.findByEmail(email), equipment, equipDesc));

    }

    public List<EquipmentRepresent> equipmentToEquipmentRepresent(List<Equipment> equipments,UUID executorId){
        List<EquipmentRepresent> equipmentRepresents=new ArrayList<>();

        for(Equipment equipment:equipments){
            ExecutorEquipment executorEquipment=executorEquipmentRepo.findByExecutorIdAndEquipmentId(executorId,equipment.getId());
            equipmentRepresents.add(new EquipmentRepresent(equipment.getId(),equipment.getEquipName(),equipment.getHeight(),equipment.getWidth(),equipment.getLength(),executorEquipment.getEquipDesc()));
        }
        return equipmentRepresents;
    }

}
