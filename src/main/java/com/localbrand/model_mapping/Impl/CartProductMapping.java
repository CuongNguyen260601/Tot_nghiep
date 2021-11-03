package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.CartProductDTO;
import com.localbrand.entity.CartProduct;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class CartProductMapping implements Mapping<CartProductDTO, CartProduct> {
    @Override
    public CartProductDTO toDto(CartProduct cartProduct) {

        return CartProductDTO
                .builder()
                .idCart(cartProduct.getIdCart())
                .idCartProduct(cartProduct.getIdCartProduct())
                .quantity(cartProduct.getQuantity())
                .idProductDetail(cartProduct.getIdProductDetail())
                .build();

    }

    @Override
    public CartProduct toEntity(CartProductDTO cartProductDTO) {
        return CartProduct
                .builder()
                .idCart(cartProductDTO.getIdCart())
                .idCartProduct(cartProductDTO.getIdCartProduct())
                .quantity(cartProductDTO.getQuantity())
                .idProductDetail(cartProductDTO.getIdProductDetail())
                .build();
    }
}
