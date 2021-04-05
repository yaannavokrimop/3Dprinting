package com.netcracker.educ.printing.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ResponseId implements Serializable {

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "executor_id")
    private UUID executorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || getClass() != o.getClass()) return false;
        ResponseId that = (ResponseId) o;
        return orderId.equals(that.orderId) && executorId.equals(that.executorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, executorId);
    }
}
