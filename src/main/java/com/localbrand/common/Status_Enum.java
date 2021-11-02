package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status_Enum {

    DELETE(1), // Đã xóa
    EXISTS(2), // Tồn tại
    EXPIRED(3), // Hết hạn
    USED(4), // Sử dụng
    OWN (5), // Sở hữu
    PROCESSING (6), // Đang xử lý
    CONFIRMED (7), // Đã xác nhận
    DELIVERY (8), // Đang giao
    PAID (9), // Đã thanh toán
    CANCEL(10); // Hủy

    @Getter
    private final Integer code;

}
