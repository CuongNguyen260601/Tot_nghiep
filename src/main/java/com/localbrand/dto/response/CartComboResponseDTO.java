package com.localbrand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartComboResponseDTO {

    private Long idCartCombo;

    private Integer idCart;

    private ComboResponseDTO comboDTO;

    private Integer quantity;;
}
