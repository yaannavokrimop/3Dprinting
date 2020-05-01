package com.netcracker.educ.printing.model.representationModel;

import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.bean.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderRepresent implements Pageable {

    private UUID id;

    @NotNull
    private float sum;

    @NotNull
    private String description;

    @NotNull
    private int height;

    @NotNull
    private int width;

    @NotNull
    private int length;

    @NotNull
    private String material;

    private int responsesCount;

    private OrderStatus status;

    private String file;

    private UUID customerId;

    private Date date;
}
