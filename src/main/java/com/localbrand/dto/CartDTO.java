package com.localbrand.dto;

import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    @Min(value = 1, message = Notification.Cart.Validate_Cart.VALIDATE_ID_CART)
    private Long idCart;

    @NotNull(message = Notification.Cart.Validate_Cart.VALIDATE_ID_USER)
    @Min(value = 1, message = Notification.Cart.Validate_Cart.VALIDATE_ID_CART)
    private Integer idUser;


    @NotNull(message = Notification.Cart.Validate_Cart.VALIDATE_ID_STATUS)
    @Min(value = 1, message = Notification.Cart.Validate_Cart.VALIDATE_ID_STATUS)
    private Integer idStatus;

}
