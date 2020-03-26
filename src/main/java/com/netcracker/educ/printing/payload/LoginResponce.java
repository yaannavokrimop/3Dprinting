package com.netcracker.educ.printing.payload;

public class LoginResponce {
    private String accessToken;
    private String tokenType = "Bearer";

    public LoginResponce(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
