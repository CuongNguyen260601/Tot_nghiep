package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum JWT_Enum {


    ACCESS_MINUTE(1000),
    REFRESH_MINUTE(10080);

    @Getter
    private Integer value;
}
