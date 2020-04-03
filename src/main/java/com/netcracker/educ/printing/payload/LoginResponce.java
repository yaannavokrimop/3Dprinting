package com.netcracker.educ.printing.payload;

import lombok.Data;

@Data
public class LoginResponce {
    private String accessToken;
    private String tokenType = "Bearer";

    public LoginResponce(String accessToken) {
        this.accessToken = accessToken;
    }
}
