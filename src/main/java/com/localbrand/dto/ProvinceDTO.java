package com.localbrand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvinceDTO {

    private Integer idProvince;

    private String nameProvince;

    private String codeProvince;
}
