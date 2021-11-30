package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.AddressDTO;
import com.localbrand.dto.CommuneDTO;
import com.localbrand.dto.request.AddressRequestDTO;
import com.localbrand.entity.Address;
import com.localbrand.entity.Commune;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.CommuneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AddressMapping implements Mapping<AddressDTO, Address> {

    private final CommuneRepository communeRepository;
    private final CommuneMapping communeMapping;

    @Override
    public AddressDTO toDto(Address address) {

        Commune commune = this.communeRepository.findById(address.getIdCommune())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.ADDRESS_IS_NULL));
        CommuneDTO communeDTO = this.communeMapping.toDto(commune);
        return AddressDTO
                .builder()
                .idAddress(address.getIdAddress())
                .province(communeDTO.getProvince())
                .commune(communeDTO)
                .district(communeDTO.getDistrict())
                .detailAddress(address.getDetailAddress())
                .build();
    }

    @Override
    public Address toEntity(AddressDTO addressDTO) {
        return Address
                .builder()
                .idAddress(addressDTO.getIdAddress())
                .idProvince(addressDTO.getProvince().getIdProvince())
                .idDistrict(addressDTO.getDistrict().getIdDistrict())
                .idCommune(addressDTO.getCommune().getIdCommune())
                .detailAddress(addressDTO.getDetailAddress())
                .build();
    }

    public Address toEntitySave(AddressRequestDTO addressRequestDTO) {
        return Address
                .builder()
                .idAddress(addressRequestDTO.getIdAddress())
                .idProvince(addressRequestDTO.getIdProvince())
                .idCommune(addressRequestDTO.getIdCommune())
                .idDistrict(addressRequestDTO.getIdDistrict())
                .detailAddress(addressRequestDTO.getDetailAddress())
                .build();
    }
}
