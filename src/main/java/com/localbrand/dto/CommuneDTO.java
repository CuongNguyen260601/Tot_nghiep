package com.localbrand.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommuneDTO {

    private Integer idCommune;

    private String nameCommune;

    private String prefixCommune;

    private DistrictDTO district;

    private ProvinceDTO province;

}
