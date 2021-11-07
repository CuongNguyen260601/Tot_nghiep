package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.ProvinceDTO;
import com.localbrand.entity.Province;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class ProvinceMapping implements Mapping<ProvinceDTO, Province> {
    @Override
    public ProvinceDTO toDto(Province province) {
        return ProvinceDTO
                .builder()
                .idProvince(province.getIdProvince())
                .nameProvince(province.getNameProvince())
                .codeProvince(province.getCodeProvince())
                .build();
    }

    @Override
    public Province toEntity(ProvinceDTO provinceDTO) {
        return Province
                .builder()
                .idProvince(provinceDTO.getIdProvince())
                .nameProvince(provinceDTO.getNameProvince())
                .codeProvince(provinceDTO.getCodeProvince())
                .build();
    }
}
