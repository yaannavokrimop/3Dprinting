package com.netcracker.educ.printing.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.bean.Pageable;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "orders")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Order  {
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
    private String name;

    @Column
    private String description;


    @ManyToMany
    @JoinTable(
            name = "mat_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "mat_id")
    )
    Set<Material> materials;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Float.compare(order.sum, sum) == 0 &&
                height == order.height &&
                width == order.width &&
                length == order.length &&
                Objects.equals(id, order.id) &&
                status == order.status &&
                Objects.equals(date, order.date) &&
                Objects.equals(file, order.file) &&
                Objects.equals(description, order.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, sum, date, height, width, length, file, description);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", sum=" + sum +
                ", date=" + date +
                ", height=" + height +
                ", width=" + width +
                ", length=" + length +
                ", file='" + file + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Order(OrderRepresent orderRepresent,OrderStatus orderStatus,Date date,Set<Material> materials,User user) {
        this.id = orderRepresent.getId();
        this.user = user;
        this.status = orderStatus ;
        this.date = date;
        this.description = orderRepresent.getDescription();
        this.materials = materials;
        this.sum=orderRepresent.getSum();
        this.height=orderRepresent.getHeight();
        this.width=orderRepresent.getWidth();
        this.length=orderRepresent.getLength();
        this.name=orderRepresent.getName();
    }
}
