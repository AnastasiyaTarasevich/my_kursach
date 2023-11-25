package com.example.my_kursach.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority

{
    ADMIN, USER, SUPPLIER;

    @Override
    public String getAuthority() {
        return name();
    }
}
