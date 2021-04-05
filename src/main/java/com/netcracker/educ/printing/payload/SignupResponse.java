package com.netcracker.educ.printing.payload;

import lombok.Data;

@Data
public class SignupResponse {
    private Boolean success;
    private String message;

    public SignupResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
