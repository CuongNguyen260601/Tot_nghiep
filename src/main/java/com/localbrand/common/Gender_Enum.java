package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender_Enum {

    NAM(1),
    NU(2),
    UNISEX(3);

    @Getter
    private final Integer code;
}
