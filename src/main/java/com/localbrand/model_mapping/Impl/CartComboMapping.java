package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.*;
import com.localbrand.dto.response.CartComboResponseDTO;
import com.localbrand.dto.response.ComboResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.ComboDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartComboMapping implements Mapping<CartComboDTO, CartCombo> {

    private final ComboMapping comboMapping;
    private final ComboDetailMapping comboDetailMapping;
    private final ComboDetailRepository comboDetailRepository;

    @Override
    public CartComboDTO toDto(CartCombo cartCombo) {
        return CartComboDTO
                .builder()
                .idCart(cartCombo.getIdCart())
                .idCartCombo(cartCombo.getIdCartCombo())
                .quantity(cartCombo.getQuantity())
                .build();
    }

    @Override
    public CartCombo toEntity(CartComboDTO cartComboDTO) {
        return CartCombo
                .builder()
                .idCart(cartComboDTO.getIdCart())
                .idCartCombo(cartComboDTO.getIdCartCombo())
                .idCombo(cartComboDTO.getComboDTO().getIdCombo().intValue())
                .quantity(cartComboDTO.getQuantity())
                .build();
    }

    public CartComboDTO toDtoCartCombo(CartCombo cartCombo, Combo combo){
        CartComboDTO cartComboDTO = this.toDto(cartCombo);

        List<ComboDetail> listComboDetail = comboDetailRepository.findAllByIdCombo(combo.getIdCombo().intValue());
        ComboResponseDTO comboResponseDTO = comboMapping.toDto(combo,null,listComboDetail.stream().map(comboDetailMapping::toDto).collect(Collectors.toList()));

        cartComboDTO.setComboDTO(comboResponseDTO);

        return cartComboDTO;
    }

    public CartComboResponseDTO toCartUserDTO(CartCombo cartCombo, Combo combo){

        List<ComboDetail> listComboDetail = comboDetailRepository.findAllByIdCombo(combo.getIdCombo().intValue());

        return CartComboResponseDTO
                .builder()
                .idCart(cartCombo.getIdCart())
                .idCartCombo(cartCombo.getIdCartCombo())
                .comboDTO(this.comboMapping.toDto(combo,null,listComboDetail.stream().map(comboDetailMapping::toDto).collect(Collectors.toList())))
                .quantity(cartCombo.getQuantity())
                .build();
    }
}
