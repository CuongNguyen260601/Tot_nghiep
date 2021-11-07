package com.localbrand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

    private Long idAddress;

    private ProvinceDTO province;

    private DistrictDTO district;

    private CommuneDTO commune;

    private String detailAddress;

}
