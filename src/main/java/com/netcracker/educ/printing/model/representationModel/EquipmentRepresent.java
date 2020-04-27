package com.netcracker.educ.printing.model.representationModel;

import com.netcracker.educ.printing.model.entity.Material;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import java.util.List;
import java.util.UUID;
@Data
public class EquipmentRepresent {
    private UUID id;
    private String equipName;
    private int height;
    private int width;
    private int length;
    private String equipDesc;
    private List<String> materialList;


    public EquipmentRepresent(String equipName, int height, int width, int length, String equipDesc,List<String> materialList) {
        this.equipName = equipName;
        this.height = height;
        this.width = width;
        this.length = length;
        this.equipDesc = equipDesc;
        this.materialList=materialList;
    }

    public EquipmentRepresent() {
    }

    public EquipmentRepresent(UUID id, String equipName, int height, int width, int length, String equipDesc) {
        this.id = id;
        this.equipName = equipName;
        this.height = height;
        this.width = width;
        this.length = length;
        this.equipDesc = equipDesc;
    }

    public EquipmentRepresent(String equipName, String equipDesc, List<String> materialList) {
        this.equipName = equipName;
        this.equipDesc = equipDesc;
        this.materialList = materialList;
    }

    public EquipmentRepresent(UUID id, String equipName, int height, int width, int length, String equipDesc, List<String> materialList) {
        this.id = id;
        this.equipName = equipName;
        this.height = height;
        this.width = width;
        this.length = length;
        this.equipDesc = equipDesc;
        this.materialList = materialList;
    }
}
