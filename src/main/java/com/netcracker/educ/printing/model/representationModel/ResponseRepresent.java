package com.netcracker.educ.printing.model.representationModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseRepresent {
    @NotNull
    private UUID executorId;

    @NotNull
    private UUID orderId;

    @NotNull
    private float sum;

    private boolean isExecutor;

}
