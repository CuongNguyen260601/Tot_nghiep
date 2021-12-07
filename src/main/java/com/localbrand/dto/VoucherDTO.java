package com.localbrand.dto;

import com.localbrand.exception.Notification;
import lombok.*;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherDTO {

    @Min(value = 1, message = Notification.Voucher.Validate_Voucher.VALIDATE_ID)
    private Long idVoucher;

    @NotBlank(message = Notification.Voucher.Validate_Voucher.VALIDATE_NAME)
    private String nameVoucher;

    @NotNull(message = Notification.Voucher.Validate_Voucher.VALIDATE_CONDITION)
    @Min(value = 1, message = Notification.Voucher.Validate_Voucher.VALIDATE_CONDITION)
    private Float condition;

    @NotNull(message = Notification.Voucher.Validate_Voucher.VALIDATE_DISCOUNT)
    @Min(value = 1, message = Notification.Voucher.Validate_Voucher.VALIDATE_DISCOUNT)
    @Max(value = 100, message = Notification.Voucher.Validate_Voucher.VALIDATE_DISCOUNT)
    private Float discount;

    @NotNull(message = Notification.Voucher.Validate_Voucher.VALIDATE_STATUS)
    @Min(value = 1, message = Notification.Voucher.Validate_Voucher.VALIDATE_STATUS)
    @Max(value = 10, message = Notification.Voucher.Validate_Voucher.VALIDATE_STATUS)
    private Integer idStatus;

    private String descriptionVoucher;

    private String codeVoucher;
}
