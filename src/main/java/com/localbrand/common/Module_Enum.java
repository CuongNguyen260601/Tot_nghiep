package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Module_Enum {

    COLOR(1),
    SIZE(2),
    CATEGORY(3),
    SALE(4),
    VOUCHER(5),
    PRODUCT(6),
    CART(7),
    BILL(8),
    COMBO(9),
    ROLE(10),
    NEW(11);

    @Getter
    private Integer module;
}
