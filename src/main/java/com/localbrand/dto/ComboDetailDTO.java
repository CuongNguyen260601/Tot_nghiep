package com.localbrand.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboDetailDTO {

    private Long idComboDetail;

    private Integer idCombo;

    private ProductDetailDTO productDetailDTO;
}
