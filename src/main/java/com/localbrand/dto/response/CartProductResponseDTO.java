package com.localbrand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductResponseDTO {

    private Long idCartProduct;

    private Integer idCart;

    private ProductDetailUserDTO productDetailDTO;

    private Integer quantity;

    private Boolean nextPage;
}
