package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Action_Enum {

    CREATE(1),
    UPDATE(2),
    READ(3),
    DELETE(4),
    CANCEL(5);

    @Getter
    private Integer action;
}
