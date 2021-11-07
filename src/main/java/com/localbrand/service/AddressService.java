package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CommuneDTO;
import com.localbrand.dto.DistrictDTO;
import com.localbrand.dto.ProvinceDTO;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    ServiceResult<List<ProvinceDTO>> getAllProvince();

    ServiceResult<List<DistrictDTO>> getAllDistrictByIdProvince(Optional<Integer> idProvince);

    ServiceResult<List<CommuneDTO>> getAllCommuneByIdProvinceAndIdDistrict(Optional<Integer> idProvince, Optional<Integer> idDistrict);
}
