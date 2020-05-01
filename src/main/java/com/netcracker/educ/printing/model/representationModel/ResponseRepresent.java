package com.netcracker.educ.printing.model.representationModel;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.netcracker.educ.printing.model.bean.Pageable;
import com.netcracker.educ.printing.model.bean.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseRepresent implements Pageable {
    @NotNull
    private UUID executorId;

    @NotNull
    private UUID orderId;

    @NotNull
    private float sum;

    @NotNull
    @JsonProperty
    private boolean isExecutor;

    private String executorInfo;

    private Date date;

    private ResponseStatus status;

    public ResponseRepresent(UUID id, float sum, String s, Date date, ResponseStatus status) {
        this.executorId = id;
        this.sum = sum;
        this.executorInfo = s;
        this.date = date;
        this.status = status;
    }
}
