package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role_Id_Enum {

    ROLE_ADMIN(1),
    ROLE_EMPLOYEE(2),
    ROLE_USER(3);

    @Getter
    private Integer id;
}
