package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.entity.Equipment;
import com.netcracker.educ.printing.model.entity.ExecutorEquipment;
import com.netcracker.educ.printing.model.entity.MaterialEquipment;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.ExecutorEquipmentRepo;
import com.netcracker.educ.printing.model.repository.MaterialEquipmentRepo;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class MaterialService {

    private MaterialRepo materialRepo;
    private MaterialEquipmentRepo materialEquipmentRepo;
    private ExecutorEquipmentRepo executorEquipmentRepo;

    public MaterialService(MaterialRepo materialRepo, MaterialEquipmentRepo materialEquipmentRepo, ExecutorEquipmentRepo executorEquipmentRepo) {
        this.materialRepo = materialRepo;
        this.materialEquipmentRepo = materialEquipmentRepo;
        this.executorEquipmentRepo = executorEquipmentRepo;
    }



    public List<String> getMaterialsByTitlePart(String materialTitlePart) {
       return materialRepo.findMatTitleByMatTitleContaining(materialTitlePart);

    }

    public List<String> getMaterialsByTitlePartAndEquipment(String materialTitlePart, UUID equipId) {
        return materialEquipmentRepo.findMatTitleByEquipmentIdAndMatTitleContaining(equipId,materialTitlePart);
    }



    public List<String> getMaterialsByEquipment(UserDetailsImpl userDetails, UUID equipId ){
        ExecutorEquipment executorEquipment=executorEquipmentRepo.findByExecutorIdAndEquipmentId(userDetails.getId(),equipId);
        Set<MaterialEquipment> materialEquipments=executorEquipment.getMatEquips();
         List<String> materials=new ArrayList<>();
         for(MaterialEquipment m:materialEquipments){
             materials.add(m.getMaterial().getMatTitle());
         }
         return materials;
    }
}
