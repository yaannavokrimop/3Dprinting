package com.netcracker.educ.printing.model.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class AddressId implements Serializable {

    private City city;

    private User user;
}
