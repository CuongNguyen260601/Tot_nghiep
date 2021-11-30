package com.localbrand.model_mapping.Impl;


import com.localbrand.dto.response.ComboDetailResponseDTO;
import com.localbrand.entity.ComboDetail;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComboDetailMapping implements Mapping<ComboDetailResponseDTO, ComboDetail> {

    private final ProductDetailRepository productDetailRepository;
    private final ProductMapping productMapping;

    @Override
    public ComboDetailResponseDTO toDto(ComboDetail comboDetail) {
        ComboDetailResponseDTO comboDetailResponseDTO = ComboDetailResponseDTO
                .builder()
                .idComboDetail(comboDetail.getIdComboDetail())
                .idCombo(comboDetail.getIdCombo())
                .build();
        comboDetailResponseDTO.setProductChildResponseDTO(productMapping.toProductChild(productDetailRepository.getById(comboDetail.getIdProductDetail().longValue())));
        return comboDetailResponseDTO;
    }

    @Override
    public ComboDetail toEntity(ComboDetailResponseDTO comboDetailResponseDTO) {

        return null;
    }
}
