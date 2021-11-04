package com.localbrand.dto;

import com.localbrand.exception.Notification;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductDTO {

    @Min(value = 1, message = Notification.Cart.Validate_Cart_Product.VALIDATE_ID_CART_PRODUCT)
    private Long idCartProduct;

    @NotNull(message = Notification.Cart.Validate_Cart.VALIDATE_ID_CART)
    @Min(value = 1, message = Notification.Cart.Validate_Cart.VALIDATE_ID_CART)
    private Integer idCart;

    @NotNull(message = Notification.Cart.Validate_Cart_Product.VALIDATE_PRODUCT_DETAIL)
    private ProductDetailDTO productDetailDTO;

    @NotNull(message = Notification.Cart.Validate_Cart_Product.VALIDATE_QUANTITY)
    @Min(value = 1, message = Notification.Cart.Validate_Cart_Product.VALIDATE_QUANTITY)
    private Integer quantity;

}