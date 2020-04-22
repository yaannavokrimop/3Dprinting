package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.entity.Equipment;
import com.netcracker.educ.printing.model.entity.ExecutorEquipment;
import com.netcracker.educ.printing.model.repository.EquipmentRepo;
import com.netcracker.educ.printing.model.representationModel.EquipmentRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.EquipmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/equipment")
@AllArgsConstructor
@Slf4j
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EquipmentRepo repo;

    @GetMapping
    public List<Equipment> getAllEquip(@RequestParam(required = false) String equipName) {

        List<Equipment> equips = new ArrayList<>();

        if (equipName == null)
            equips.addAll(repo.findAll());
        else
            equips.addAll(repo.findByEquipNameContaining(equipName));

        return equips;
    }

    @GetMapping("{id}")
    public Equipment getEquipById(@PathVariable("id") UUID id) {
        Optional<Equipment> equipmentData = repo.findById(id);

        if (equipmentData.isPresent()) {
            return equipmentData.get();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping("/my")
    public List<EquipmentRepresent> getUserEquip() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return equipmentService.getUserEquipment(principal.getId());
    }


    @PostMapping
    public Equipment createEquip(@RequestBody EquipmentRepresent inputEquip) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Equipment equipment=new Equipment(inputEquip.getEquipName(),inputEquip.getHeight(),inputEquip.getWidth(),inputEquip.getLength());
        return equipmentService.create(principal.getEmail(), equipment,inputEquip.getEquipDesc());
    }

    @PostMapping("/add")
    public ExecutorEquipment addEquip(@RequestBody Map<String, String>  inputEquip) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return equipmentService.addEquipment(principal.getEmail(),inputEquip.get("equipName"),inputEquip.get("equipDesc"));
    }

    @PutMapping("{id}")
    public Equipment updateEquipment(
            @PathVariable("id") UUID id,
            @RequestBody Equipment inputEquip
    ) {
        Optional<Equipment> equipmentData = repo.findById(id);

        if (equipmentData.isPresent()) {
            Equipment equipment = equipmentData.get();
                   equipment.setEquipName(inputEquip.getEquipName());
//                   equipment.setEquipDesc(inputEquip.getEquipDesc());
                   equipment.setHeight(inputEquip.getHeight());
                   equipment.setWidth(inputEquip.getWidth());
                   equipment.setLength(inputEquip.getLength());
            return repo.save(equipment);
        } else {
            throw new NotFoundException();
        }
    }

    @DeleteMapping("{id}")
    public UUID deleteEquip(@PathVariable("id") UUID id) {

        repo.deleteById(id);

        return id;

    }

    @GetMapping("/equipByPartName/{partName}")
    public List<String> getEquipmentByPartName(@PathVariable("partName") String equipPartName){
        return equipmentService.getEquipmentsByEquipNamePart(equipPartName);
    }

    @GetMapping("/name/{equipName}")
    public Equipment getEquipmentByName(@PathVariable("equipName") String equipName){
        return equipmentService.getEquipmentByName(equipName);
    }



}
