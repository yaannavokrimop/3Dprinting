package com.netcracker.educ.printing.payload;

import lombok.Data;

@Data
public class SignupResponse {
    private Boolean finished;
    private String message;

    public SignupResponse(Boolean finished, String message) {
        this.finished = finished;
        this.message = message;
    }
}
