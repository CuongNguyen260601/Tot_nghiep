package com.localbrand.dto;

import com.localbrand.exception.Notification;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponseDTO {

    @Min(value = 1, message = Notification.Sale.Validate_Sale.VALIDATE_ID)
    private Long idSale;

    @NotBlank(message = Notification.Sale.Validate_Sale.VALIDATE_NAME)
    private String nameSale;

    @NotNull(message = Notification.Sale.Validate_Sale.VALIDATE_DISCOUNT)
    @Min(value = 1, message = Notification.Sale.Validate_Sale.VALIDATE_DISCOUNT)
    @Max(value = 100, message = Notification.Sale.Validate_Sale.VALIDATE_DISCOUNT)
    private Float discount;

    @NotNull(message = Notification.Sale.Validate_Sale.VALIDATE_STATUS)
    @Min(value = 1,message = Notification.Sale.Validate_Sale.VALIDATE_STATUS)
    @Max(value = 10, message = Notification.Sale.Validate_Sale.VALIDATE_STATUS)
    private Integer idStatus;

    private String descriptionSale;

    private Boolean isSale;
}
