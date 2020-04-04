package com.netcracker.educ.printing.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
public class Address implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private City city;

    @Id
    @ManyToOne
    @JoinColumn
    private User user;

    private String description;
}
