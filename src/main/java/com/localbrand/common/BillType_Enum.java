package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BillType_Enum {

    BILL_USER(0),
    BILL_ADMIN(1);

    @Getter
    private Integer type;
}
