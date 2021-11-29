package com.localbrand.model_mapping.Impl;


import com.localbrand.dto.request.ComboRequestDTO;
import com.localbrand.dto.response.ComboDetailResponseDTO;
import com.localbrand.dto.response.ComboResponseDTO;
import com.localbrand.entity.Combo;
import com.localbrand.entity.ComboTag;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComboMapping implements Mapping<ComboResponseDTO, Combo> {

    @Override
    public ComboResponseDTO toDto(Combo combo) {
        return ComboResponseDTO
                .builder()
                .idCombo(combo.getIdCombo())
                .nameCombo(combo.getNameCombo())
                .price(combo.getPrice())
                .descriptionCombo(combo.getDescriptionCombo())
                .createAt(combo.getCreateAt())
                .idStatus(combo.getIdStatus())
                .frontPhoto(combo.getFrontPhoto())
                .backPhoto(combo.getBackPhoto())
                .coverPhoto(combo.getCoverPhoto())
                .quantity(combo.getQuantity())
                .build();
    }

    public ComboResponseDTO toDto(Combo combo, List<ComboTag> listComboTag, List<ComboDetailResponseDTO> comboDetailResponseDTO) {
        ComboResponseDTO comboResponseDTO = ComboResponseDTO
                .builder()
                .idCombo(combo.getIdCombo())
                .nameCombo(combo.getNameCombo())
                .price(combo.getPrice())
                .descriptionCombo(combo.getDescriptionCombo())
                .createAt(combo.getCreateAt())
                .idStatus(combo.getIdStatus())
                .frontPhoto(combo.getFrontPhoto())
                .backPhoto(combo.getBackPhoto())
                .coverPhoto(combo.getCoverPhoto())
                .quantity(combo.getQuantity())
                .build();
        comboResponseDTO.setListComboTag(listComboTag);
        comboResponseDTO.setComboDetailResponseDTO(comboDetailResponseDTO);
        return comboResponseDTO;
    }

    @Override
    public Combo toEntity(ComboResponseDTO comboResponseDTO) {
        return Combo
                .builder()
                .idCombo(comboResponseDTO.getIdCombo())
                .nameCombo(comboResponseDTO.getNameCombo())
                .price(comboResponseDTO.getPrice())
                .idStatus(comboResponseDTO.getIdStatus())
                .descriptionCombo(comboResponseDTO.getDescriptionCombo())
                .createAt(comboResponseDTO.getCreateAt())
                .frontPhoto(comboResponseDTO.getFrontPhoto())
                .backPhoto(comboResponseDTO.getBackPhoto())
                .coverPhoto(comboResponseDTO.getCoverPhoto())
                .quantity(comboResponseDTO.getQuantity())
                .build();
    }

    public Combo toEntity(ComboRequestDTO comboRequestDTO) {
        return Combo
                .builder()
                .idCombo(comboRequestDTO.getIdCombo())
                .nameCombo(comboRequestDTO.getNameCombo())
                .price(comboRequestDTO.getPrice())
                .idStatus(comboRequestDTO.getIdStatus())
                .descriptionCombo(comboRequestDTO.getDescriptionCombo())
                .createAt(comboRequestDTO.getCreateAt())
                .frontPhoto(comboRequestDTO.getFrontPhoto())
                .backPhoto(comboRequestDTO.getBackPhoto())
                .coverPhoto(comboRequestDTO.getCoverPhoto())
                .quantity(comboRequestDTO.getQuantity())
                .build();
    }
}
