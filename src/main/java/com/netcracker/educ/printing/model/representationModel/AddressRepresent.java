package com.netcracker.educ.printing.model.representationModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
@Data
@AllArgsConstructor
public class AddressRepresent {
    private String city;
    private String description;
    private UUID userId;


}


