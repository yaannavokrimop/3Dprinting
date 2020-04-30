package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.model.entity.Material;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/material")
public class MaterialController {

    private MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/materialList/{titlePart}")
    public List<String> getMaterialNames(@PathVariable("titlePart") String materialTitlePart){
        return materialService.getMaterialsByTitlePart(materialTitlePart);
    }

    @GetMapping(value = "/equip/{equipmentId}/materialList/{titlePart}")
    public List<String> getMaterialNamesForEquip(@PathVariable("equipmentId") UUID equipId,
                                                 @PathVariable("titlePart") String materialTitlePart){
        return materialService.getMaterialsByTitlePartAndEquipment(materialTitlePart,equipId);
    }

    @GetMapping("/executor/")
    public Set<String> getMaterialsByExecutor(@AuthenticationPrincipal UserDetailsImpl executor){
        return  materialService.getMaterialsByUser(executor.getId());
    }



}
