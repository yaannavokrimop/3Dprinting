package com.netcracker.educ.printing.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ChatId implements Serializable {
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "executor_id")
    private UUID executorId;
}
