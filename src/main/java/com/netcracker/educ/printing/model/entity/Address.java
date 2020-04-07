package com.netcracker.educ.printing.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @EmbeddedId
    private AddressId id;



    @MapsId("city_id")
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @MapsId("user_id")
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;


    private String description;
}
