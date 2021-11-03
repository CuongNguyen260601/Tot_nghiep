package com.localbrand.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartComboDTO {

    private Long idCartCombo;

    private Integer idCart;

    private Integer idCombo;
}