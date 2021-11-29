package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.DistrictDTO;
import com.localbrand.entity.District;
import com.localbrand.entity.Province;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DistrictMapping implements Mapping<DistrictDTO, District> {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapping provinceMapping;

    @Override
    public DistrictDTO toDto(District district) {
        Province province = this.provinceRepository.findById(district.getIdProvince())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.PROVINCE_IS_NULL));

        return DistrictDTO
                .builder()
                .nameDistrict(district.getNameDistrict())
                .idDistrict(district.getIdDistrict())
                .prefixDistrict(district.getPrefixDistrict())
                .provinceDTO(this.provinceMapping.toDto(province))
                .build();
    }

    @Override
    public District toEntity(DistrictDTO districtDTO) {
        return District
                .builder()
                .idDistrict(districtDTO.getIdDistrict())
                .nameDistrict(districtDTO.getNameDistrict())
                .prefixDistrict(districtDTO.getPrefixDistrict())
                .idProvince(districtDTO.getProvinceDTO().getIdProvince())
                .build();
    }
}
