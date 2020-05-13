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
        Equipment equipmentDb=equipmentRepo.save(equipment);
        User executor = userRepo.findByEmail(userEmail);

        for(String material:materialName) {
            materialEquipmentRepo.save(new MaterialEquipment(equipmentDb,materialRepo.findByMatTitle(material)));
        }
        ExecutorEquipment executorEquipment = new ExecutorEquipment(executor, equipmentDb,equipDesc,materialEquipmentRepo.findByMaterialNames(materialName,equipmentDb.getEquipName()));
        executorEquipmentRepo.save(executorEquipment);
        log.info("User {} created equipment {}",executor.getId(),equipmentDb.getId());
        return equipmentDb;
    }

    public List<EquipmentRepresent> getUserEquipment(UUID userId) {
        List<ExecutorEquipment>executorEquipmentList= executorEquipmentRepo.findAllByExecutorId(userId);
        log.info("Get equipments for user {}",userId);
        return executorEquipmentsToEquipmentRepresent(executorEquipmentList);
}

    public List<String> getEquipmentsByEquipNamePart(String equipName) {
        List<String> equipments= equipmentRepo.findEquipNameByEquipNameContaining(equipName);
        log.info("Get equipment by part of name: {}",equipName);
        return equipments;
    }

    public EquipmentRepresent getEquipmentByExecutorEquipId(UUID executorEquipId) {
      ExecutorEquipment executorEquipment=Objects.requireNonNull( executorEquipmentRepo.findById(executorEquipId).orElse(null),"executorEquipment must not be null");
      log.info("Get Equipment by executorEquipId= {}",executorEquipId);
      return executorEquipmentToEquipmentRepresent(executorEquipment);


    }

    public void addEquipment(String email, String equipName, String equipDesc,List<String> materials) {
            Equipment equipment = equipmentRepo.findByEquipName(equipName);
        Set<MaterialEquipment> matEquips = materialEquipmentRepo.findByMaterialNames(materials,equipName);
             executorEquipmentRepo.save(new ExecutorEquipment(userRepo.findByEmail(email), equipment, equipDesc,matEquips));
            log.info("User {} added equipment {}",email,equipment.getId());
    }

    public List<EquipmentRepresent> executorEquipmentsToEquipmentRepresent(List<ExecutorEquipment> executorEquipments){
        log.debug("ExecutorEquipments to EquipmentsRepresent");
        List<EquipmentRepresent> equipmentRepresents=new ArrayList<>();
        for(ExecutorEquipment exEquipment:executorEquipments){
            equipmentRepresents.add(executorEquipmentToEquipmentRepresent(exEquipment));
        }
        return equipmentRepresents;
    }

    public EquipmentRepresent executorEquipmentToEquipmentRepresent(ExecutorEquipment executorEquipment) {
        log.debug("ExecutorEquipment to EquipmentRepresent");
        Equipment equipment=executorEquipment.getEquipment();
            return new EquipmentRepresent(
                    equipment.getId(),
                    equipment.getEquipName(),
                    equipment.getHeight(),
                    equipment.getWidth(),
                    equipment.getLength(),
                    executorEquipment.getEquipDesc(),
                    getMaterialsByExecutorEquipment(executorEquipment),
                    executorEquipment.getId());

    }

    public List<String> getMaterialsByExecutorEquipment(ExecutorEquipment executorEquipment ){
        Set<MaterialEquipment> materialEquipments=executorEquipment.getMatEquips();
        List<String> materials=new ArrayList<>();
        for(MaterialEquipment m:materialEquipments){
            materials.add(m.getMaterial().getMatTitle());
        }
        log.info("Get materials by executorEquipment {}",executorEquipment.getId());
        return materials;
    }


    public void deleteById(UUID executorEquipId) {
            executorEquipmentRepo.deleteById(executorEquipId);
            log.info("ExecutorEquipment {} was deleted",executorEquipId);
    }

    public Equipment getEquipmentByName(String equipName,UUID userId) {
        Equipment equipment= equipmentRepo.findByEquipName(equipName);
        log.info("Get equipment by name: {}",equipName);
        return equipment;

    }
}
