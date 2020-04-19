package com.netcracker.educ.printing.model.representationModel;

import com.netcracker.educ.printing.model.entity.Material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
public class OrderRepresent {
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
}
