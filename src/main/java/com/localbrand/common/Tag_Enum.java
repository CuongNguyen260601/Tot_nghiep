package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Tag_Enum {

    SALE(1), // Đang sale
    HOT(2), // Hot
    OUT_OF_STOCK(3),// Hết hàng
    NOT_ENOUGH (4); // Thiếu hàng

    @Getter
    private final Integer code;

}
