package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.CartDTO;
import com.localbrand.entity.Cart;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class CartMapping implements Mapping<CartDTO, Cart> {
    @Override
    public CartDTO toDto(Cart cart) {
        return CartDTO
                .builder()
                .idCart(cart.getIdCart())
                .idUser(cart.getIdUser())
                .idStatus(cart.getIdStatus())
                .build();
    }

    @Override
    public Cart toEntity(CartDTO cartDTO) {
        return Cart
                .builder()
                .idCart(cartDTO.getIdCart())
                .idUser(cartDTO.getIdUser())
                .idStatus(cartDTO.getIdStatus())
                .build();
    }
}
