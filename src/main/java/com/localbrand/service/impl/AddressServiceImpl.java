package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CommuneDTO;
import com.localbrand.dto.DistrictDTO;
import com.localbrand.dto.ProvinceDTO;
import com.localbrand.entity.Commune;
import com.localbrand.entity.District;
import com.localbrand.entity.Province;
import com.localbrand.model_mapping.Impl.CommuneMapping;
import com.localbrand.model_mapping.Impl.DistrictMapping;
import com.localbrand.model_mapping.Impl.ProvinceMapping;
import com.localbrand.repository.CommuneRepository;
import com.localbrand.repository.DistrictRepository;
import com.localbrand.repository.ProvinceRepository;
import com.localbrand.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapping provinceMapping;
    private final DistrictRepository districtRepository;
    private final DistrictMapping districtMapping;
    private final CommuneRepository communeRepository;
    private final CommuneMapping communeMapping;

    @Override
    public ServiceResult<List<ProvinceDTO>> getAllProvince() {
        List<Province> lstProvinces = this.provinceRepository.findAll();
        return new ServiceResult<>(HttpStatus.OK, "", lstProvinces.stream().map(this.provinceMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<DistrictDTO>> getAllDistrictByIdProvince(Optional<Integer> idProvince) {

        if(idProvince.isEmpty() || idProvince.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,"", null);
        }
        List<District> listDistricts = this.districtRepository.findAllByIdProvince(idProvince.get());

        return new ServiceResult<>(HttpStatus.OK, "", listDistricts.stream().map(this.districtMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<CommuneDTO>> getAllCommuneByIdProvinceAndIdDistrict(Optional<Integer> idProvince, Optional<Integer> idDistrict) {

        if(idProvince.isEmpty() || idProvince.get() < 1 || idDistrict.isEmpty() || idDistrict.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,"", null);
        }
        List<Commune> listCommune = this.communeRepository.findAllByIdProvinceAndAndIdDistrict(idProvince.get(), idDistrict.get());

        return new ServiceResult<>(HttpStatus.OK, "", listCommune.stream().map(this.communeMapping::toDto).collect(Collectors.toList()));

    }
}
