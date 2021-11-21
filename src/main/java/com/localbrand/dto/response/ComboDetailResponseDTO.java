package com.localbrand.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.localbrand.dto.ProductDetailDTO;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboDetailResponseDTO {

    private Long idComboDetail;

    private Integer idCombo;

    @JsonProperty("productDetail")
    private ProductChildResponseDTO productChildResponseDTO;
}
