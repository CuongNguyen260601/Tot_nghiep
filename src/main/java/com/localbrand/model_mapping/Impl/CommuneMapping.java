package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.CommuneDTO;
import com.localbrand.dto.DistrictDTO;
import com.localbrand.entity.Commune;
import com.localbrand.entity.District;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommuneMapping implements Mapping<CommuneDTO, Commune> {

    private final DistrictRepository districtRepository;
    private final DistrictMapping districtMapping;

    @Override
    public CommuneDTO toDto(Commune commune) {

        District district = this.districtRepository.findById(commune.getIdDistrict())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.DISTRICT_IS_NULL));

        DistrictDTO districtDTO = this.districtMapping.toDto(district);

        return CommuneDTO
                .builder()
                .idCommune(commune.getIdCommune())
                .nameCommune(commune.getNameCommune())
                .prefixCommune(commune.getPrefixCommune())
                .district(districtDTO)
                .province(districtDTO.getProvinceDTO())
                .build();

    }

    @Override
    public Commune toEntity(CommuneDTO communeDTO) {
        return Commune
                .builder()
                .idCommune(communeDTO.getIdCommune())
                .nameCommune(communeDTO.getNameCommune())
                .prefixCommune(communeDTO.getPrefixCommune())
                .idDistrict(communeDTO.getDistrict().getIdDistrict())
                .idProvince(communeDTO.getProvince().getIdProvince())
                .build();
    }
}
