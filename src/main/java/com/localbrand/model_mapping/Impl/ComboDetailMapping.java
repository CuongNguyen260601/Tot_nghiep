package com.localbrand.model_mapping.Impl;


import com.localbrand.dto.response.ComboDetailResponseDTO;
import com.localbrand.entity.ComboDetail;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.ProductDetailRepository;
import org.springframework.stereotype.Component;

@Component
public class ComboDetailMapping implements Mapping<ComboDetailResponseDTO, ComboDetail> {

    private final ProductDetailRepository productDetailRepository;
    private final ProductMapping productMapping;

    public ComboDetailMapping(ProductDetailRepository productDetailRepository, ProductMapping productMapping) {
        this.productDetailRepository = productDetailRepository;
        this.productMapping = productMapping;
    }

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
