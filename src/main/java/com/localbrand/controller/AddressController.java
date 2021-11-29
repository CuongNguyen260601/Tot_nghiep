package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CommuneDTO;
import com.localbrand.dto.DistrictDTO;
import com.localbrand.dto.ProvinceDTO;
import com.localbrand.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(Interface_API.Cors.CORS)
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping(Interface_API.API.Address.ADDRESS_FIND_ALL_PROVINCE)
    public ResponseEntity<ServiceResult<List<ProvinceDTO>>> getAllProvince(){
        ServiceResult<List<ProvinceDTO>> result = this.addressService.getAllProvince();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping(Interface_API.API.Address.ADDRESS_FIND_ALL_DISTRICT)
    public ResponseEntity<ServiceResult<List<DistrictDTO>>> getAllDistrict(@RequestParam Optional<Integer> idProvince){
        ServiceResult<List<DistrictDTO>> result = this.addressService.getAllDistrictByIdProvince(idProvince);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping(Interface_API.API.Address.ADDRESS_FIND_ALL_COMMUNE)
    public ResponseEntity<ServiceResult<List<CommuneDTO>>> getAllCommune(@RequestParam Optional<Integer> idProvince, @RequestParam Optional<Integer> idDistrict){
        ServiceResult<List<CommuneDTO>> result = this.addressService.getAllCommuneByIdProvinceAndIdDistrict(idProvince, idDistrict);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
