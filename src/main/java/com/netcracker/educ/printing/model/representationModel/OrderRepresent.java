package com.netcracker.educ.printing.model.representationModel;

import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.bean.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRepresent implements Pageable {

    private UUID id;

    @NotNull
    private float sum;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private int height;

    @NotNull
    private int width;

    @NotNull
    private int length;

    @NotNull
    private List<String> materials;

    private int responsesCount;

    private String status;

    private UUID customerId;

    private String date;

    private String file;

    public OrderRepresent(float sum, int height, int width, int length, String name) {
        this.sum = sum;
        this.height = height;
        this.width = width;
        this.length = length;
        this.name = name;
    }
}
