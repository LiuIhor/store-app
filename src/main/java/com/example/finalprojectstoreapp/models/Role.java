package com.example.finalprojectstoreapp.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    MANAGER, CLIENT;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
