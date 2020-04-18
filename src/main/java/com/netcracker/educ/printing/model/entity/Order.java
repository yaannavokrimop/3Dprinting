package com.netcracker.educ.printing.model.entity;

import com.netcracker.educ.printing.model.bean.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NonNull
    @Column(nullable = false)
    private float sum;

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

    @ManyToMany
    @JoinTable(
            name = "mat_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "mat_id")
    )
    Set<Material> materials;
}
