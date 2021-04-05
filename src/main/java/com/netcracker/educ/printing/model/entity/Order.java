package com.netcracker.educ.printing.model.entity;

import com.netcracker.educ.printing.model.bean.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private UUID id;

    @NonNull
    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NonNull
    @Column(nullable = false)
    private int sum;

    @Column(nullable = false)
    private Date date;

    @NonNull
    @Column(nullable = false)
    private int height;

    @NonNull
    @Column(nullable = false)
    private int width;

    @NonNull
    @Column(nullable = false)
    private int length;

    @Column
    private String file;

    @NonNull
    @Column
    private String description;
}
