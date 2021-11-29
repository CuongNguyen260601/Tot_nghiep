package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.CartComboDTO;
import com.localbrand.entity.CartCombo;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class CartComboMapping implements Mapping<CartComboDTO, CartCombo> {
    @Override
    public CartComboDTO toDto(CartCombo cartCombo) {
        return CartComboDTO
                .builder()
                .idCart(cartCombo.getIdCart())
                .idCartCombo(cartCombo.getIdCartCombo())
                .idCombo(cartCombo.getIdCombo())
                .build();
    }

    @Override
    public CartCombo toEntity(CartComboDTO cartComboDTO) {
        return CartCombo
                .builder()
                .idCart(cartComboDTO.getIdCart())
                .idCartCombo(cartComboDTO.getIdCartCombo())
                .build();
    }
}
