package com.netcracker.educ.printing.model.representationModel;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import java.util.UUID;
@Data
public class EquipmentRepresent {
    private UUID id;
    private String equipName;
    private int height;
    private int width;
    private int length;
    private String equipDesc;


    public EquipmentRepresent(String equipName, int height, int width, int length, String equipDesc) {
        this.equipName = equipName;
        this.height = height;
        this.width = width;
        this.length = length;
        this.equipDesc = equipDesc;
    }

    public EquipmentRepresent() {
    }
}
