package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Config_Enum {

    SIZE_PAGE (5);

    @Getter
    private final Integer code;

}
