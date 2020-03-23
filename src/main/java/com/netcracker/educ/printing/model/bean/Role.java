package com.netcracker.educ.printing.model.bean;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CUSTOMER,
    EXECUTOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
