package com.localbrand.common;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role_Enum {

    ROLE_USER("ROLE_USER"),
    ROLE_EMPLOYEE("ROLE_EMPLOYEE"),
    ROLE_ADMIN("ROLE_ADMIN");

    @Getter
    private String role;

}
