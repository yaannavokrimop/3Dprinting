package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.entity.*;
import com.netcracker.educ.printing.model.repository.*;
import com.netcracker.educ.printing.model.representationModel.EquipmentRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
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
    private final MaterialEquipmentRepo materialEquipmentRepo;
    private final MaterialRepo materialRepo;


    public Equipment create(String userEmail, Equipment equipment,String equipDesc,List<String> materialName) {
        equipmentRepo.save(equipment);
        User executor = userRepo.findByEmail(userEmail);
        ExecutorEquipment executorEquipment = new ExecutorEquipment(executor, equipment,equipDesc);
        executorEquipmentRepo.save(executorEquipment);
        for(String material:materialName) {
            materialEquipmentRepo.save(new MaterialEquipment(equipment,materialRepo.findByMatTitle(material)));
        }
        return equipment;
    }

    public List<EquipmentRepresent> getUserEquipment(UUID userId) {
        List<Equipment>equipmentList= executorEquipmentRepo.findAllByExecutorId(userId).stream()
                .map(ExecutorEquipment::getEquipment)
                .collect(Collectors.toList());
        return equipmentsToEquipmentRepresent(equipmentList,userId);
    }

    public List<String> getEquipmentsByEquipNamePart(String equipName) {
        return equipmentRepo.findEquipNameByEquipNameContaining(equipName);
    }

    public EquipmentRepresent getEquipmentById(UUID equipId,UUID userId) {
      Equipment equipment=equipmentRepo.findById(equipId).orElse(null);
      if(equipment != null){
        return equipmentToEquipmentRepresent(equipment,userId);
      }else return null;

    }

    public void addEquipment(String email, String equipName, String equipDesc,List<String> materials) {
            Equipment equipment = equipmentRepo.findByEquipName(equipName);
        Set<MaterialEquipment> matEquips = materialEquipmentRepo.findByMaterialNames(materials,equipName);
             executorEquipmentRepo.save(new ExecutorEquipment(userRepo.findByEmail(email), equipment, equipDesc,matEquips));

    }

    public List<EquipmentRepresent> equipmentsToEquipmentRepresent(List<Equipment> equipments,UUID executorId){
        List<EquipmentRepresent> equipmentRepresents=new ArrayList<>();

        for(Equipment equipment:equipments){
//            ExecutorEquipment executorEquipment=executorEquipmentRepo.findByExecutorIdAndEquipmentId(executorId,equipment.getId());
//            equipmentRepresents.add(new EquipmentRepresent(equipment.getId(),equipment.getEquipName(),equipment.getHeight(),equipment.getWidth(),equipment.getLength(),executorEquipment.getEquipDesc()));
            equipmentRepresents.add(equipmentToEquipmentRepresent(equipment,executorId));
        }
        return equipmentRepresents;
    }

    public EquipmentRepresent equipmentToEquipmentRepresent(Equipment equipment, UUID executorId) {
        ExecutorEquipment executorEquipment = executorEquipmentRepo.findByExecutorIdAndEquipmentId(executorId, equipment.getId());
        return new EquipmentRepresent(
                equipment.getId(),
                equipment.getEquipName(),
                equipment.getHeight(),
                equipment.getWidth(),
                equipment.getLength(),
                executorEquipment.getEquipDesc(),
                getMaterialsByExecutorEquipment(executorEquipment));
    }

    public List<String> getMaterialsByExecutorEquipment(ExecutorEquipment executorEquipment ){
        Set<MaterialEquipment> materialEquipments=executorEquipment.getMatEquips();
        List<String> materials=new ArrayList<>();
        for(MaterialEquipment m:materialEquipments){
            materials.add(m.getMaterial().getMatTitle());
        }
        return materials;
    }


    public void deleteById(UUID equipId,UUID userId) {
        ExecutorEquipment executorEquipment=executorEquipmentRepo.findByExecutorIdAndEquipmentId(userId,equipId);

        if(executorEquipment!=null){
            executorEquipmentRepo.deleteById(executorEquipment.getId());
        }
    }

    public Equipment getEquipmentByName(String equipName,UUID userId) {
        return equipmentRepo.findByEquipName(equipName);

    }
}
