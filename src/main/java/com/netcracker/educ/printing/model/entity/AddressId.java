package com.netcracker.educ.printing.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class AddressId implements Serializable {

    @Column(name = "city_id")
    private int cityId;

    @Column(name = "user_id")
    private UUID userId;
}
