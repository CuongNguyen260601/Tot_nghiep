package com.localbrand.dto;

import com.localbrand.exception.Notification;
import lombok.*;

import javax.validation.constraints.Min;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductDTO {

    private Long idCartProduct;
    private Integer idCart;
    private Integer idProductDetail;
    private String productName;

    @Min(value = 1, message = Notification.Cart.Cart_Product.Validate.QUANTITY_MIN)
    private Integer quantity;
    private Long idUser;

}