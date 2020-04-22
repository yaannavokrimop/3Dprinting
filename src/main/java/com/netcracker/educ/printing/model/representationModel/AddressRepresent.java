package com.netcracker.educ.printing.model.representationModel;

import lombok.Data;

import java.util.UUID;
@Data
public class AddressRepresent {
    private UUID id;
    private String city;
    private String description;
    private UUID userId;

    public AddressRepresent(String city, String description, UUID userId,UUID id) {
        this.id=id;
        this.city = city;
        this.description = description;
        this.userId = userId;
    }
}


