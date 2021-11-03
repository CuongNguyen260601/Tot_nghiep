package com.localbrand.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductDTO {

    private Long idCartProduct;

    private Integer idCart;

    private ProductDetailDTO productDetailDTO;

    private Integer quantity;

}