package com.localbrand.dto;

import com.localbrand.dto.response.ComboResponseDTO;
import com.localbrand.entity.Combo;
import com.localbrand.exception.Notification;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartComboDTO {

    @Min(value = 1, message = Notification.Cart.Validate_Cart_Combo.VALIDATE_ID_CART_COMBO)
    private Long idCartCombo;

    @NotNull(message = Notification.Cart.Validate_Cart.VALIDATE_ID_CART)
    @Min(value = 1, message = Notification.Cart.Validate_Cart.VALIDATE_ID_CART)
    private Integer idCart;

    @NotNull(message = Notification.Cart.Validate_Cart_Combo.VALIDATE_COMBO)
    private ComboResponseDTO comboDTO;

    @NotNull(message = Notification.Cart.Validate_Cart_Combo.VALIDATE_QUANTITY)
    @Min(value = 1, message = Notification.Cart.Validate_Cart_Combo.VALIDATE_QUANTITY)
    private Integer quantity;
}