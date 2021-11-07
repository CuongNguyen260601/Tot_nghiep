package com.localbrand.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestDTO {

    private Long idAddress;

    private Integer idProvince;

    private Integer idDistrict;

    private Integer idCommune;

    private String detailAddress;

}
