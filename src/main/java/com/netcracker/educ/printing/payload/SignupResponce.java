package com.netcracker.educ.printing.payload;

import lombok.Data;

@Data
public class SignupResponce {
    private Boolean success;
    private String message;

    public SignupResponce(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
