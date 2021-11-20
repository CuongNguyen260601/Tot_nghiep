package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Action_Enum {

    SAVE(1),
    READ(2),
    DELETE(3),
    CANCEL(4);

    @Getter
    private Integer action;
}
