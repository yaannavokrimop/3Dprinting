package com.netcracker.educ.printing.payload;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private List<String> authority;

    public LoginResponse(String accessToken, List<String> authority) {
        this.accessToken = accessToken;
        this.authority = authority;
    }
}
