package com.localbrand.dto.response;

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

    private ProductChildResponseDTO productChildResponseDTO;
}
