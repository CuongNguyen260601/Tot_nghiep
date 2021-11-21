package com.localbrand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.Column;
//import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictDTO {

    private Integer idDistrict;

    private String nameDistrict;

    private String prefixDistrict;

    private ProvinceDTO provinceDTO;
}
