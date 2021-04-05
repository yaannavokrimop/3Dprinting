package com.netcracker.educ.printing.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.educ.printing.model.repository.CityRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @JsonIgnore
    private City city;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    private String description;

    public Address(User user, City city, String description) {
        this.city = city;
        this.user = user;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(user, address.user) &&
                Objects.equals(description, address.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, description);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
