package com.netcracker.educ.printing.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Embeddable
public class AddressId implements Serializable {

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "user_id")
    private UUID userId;

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressId that = (AddressId) o;
        return userId.equals(that.userId) && cityId.equals(that.cityId);
    }


    @Override
    public int hashCode() {
        return Objects.hash(userId, cityId);
    }
}
