package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.entity.*;
import com.netcracker.educ.printing.model.repository.ExecutorEquipmentRepo;
import com.netcracker.educ.printing.model.repository.MaterialEquipmentRepo;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MaterialService {

    private MaterialRepo materialRepo;
    private MaterialEquipmentRepo materialEquipmentRepo;
    private ExecutorEquipmentRepo executorEquipmentRepo;
    private EquipmentService equipmentService;

    public MaterialService(MaterialRepo materialRepo, MaterialEquipmentRepo materialEquipmentRepo, ExecutorEquipmentRepo executorEquipmentRepo, EquipmentService equipmentService) {
        this.materialRepo = materialRepo;
        this.materialEquipmentRepo = materialEquipmentRepo;
        this.executorEquipmentRepo = executorEquipmentRepo;
        this.equipmentService = equipmentService;
    }

    //public List<String> getMaterialsByTitlePart(String materialTitlePart) {
      // return materialRepo.findMatTitleByMatTitleContaining(materialTitlePart);

    //}

    //public List<String> getMaterialsByTitlePartAndEquipment(String materialTitlePart, UUID equipId) {
      //  return materialEquipmentRepo.findMatTitleByEquipmentIdAndMatTitleContaining(equipId,materialTitlePart);
    //}



    public Set<String> getMaterialsByUser(UUID userId ){
        List<MaterialEquipment> materialEquipments=executorEquipmentRepo.findMaterialEquipmentByExecutorId(userId);
        Set<String> materials=new HashSet<>();
        for(MaterialEquipment matEquipment:materialEquipments){
            materials.add(matEquipment.getMaterial().getMatTitle());
        }
        return materials;
    }

    public List<String> getMaterialsByEquipment(UUID equipId) {
        return materialEquipmentRepo.findMatTitleByEquipmentId(equipId);

    }

    public List<String> getAllMaterials() {
        return materialRepo.findAllMatTitles();

    }
}
