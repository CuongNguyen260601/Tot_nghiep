package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Name_Status_Enum {


    DELETE(1, "Đã xóa"), // Đã xóa
    EXISTS(2, "Tồn tại"), // Tồn tại
    EXPIRED(3, "Hết hạn"), // Hết hạn
    USED(4, "Đã sử dụng"), // Sử dụng
    OWN (5, "Sở hữu"), // Sở hữu
    PROCESSING (6, "Đang xử lý"), // Đang xử lý
    CONFIRMED (7, "Đã xác nhận"), // Đã xác nhận
    DELIVERY (8, "Đang giao"), // Đang giao
    PAID (9, "Đã thanh toán"), // Đã thanh toán
    CANCEL(10, "Hủy"); // Hủy

    @Getter
    private final Integer code;

    @Getter
    private final String name;

    public static Name_Status_Enum findByCode(Integer code){
        for (Name_Status_Enum name_status_enum: Name_Status_Enum.values()) {
            if(name_status_enum.code.equals(code)){
                return name_status_enum;
            }
        }
        return EXISTS;
    }
}
