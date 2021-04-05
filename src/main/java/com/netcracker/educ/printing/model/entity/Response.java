package com.netcracker.educ.printing.model.entity;

import com.netcracker.educ.printing.model.bean.ResponseId;
import com.netcracker.educ.printing.model.bean.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    @EmbeddedId
    private ResponseId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @MapsId("executorId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User executor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResponseStatus status;

    @Column(nullable = false)
    private float sum;

    @Column(nullable = false)
    private Date date = new Date();
}
