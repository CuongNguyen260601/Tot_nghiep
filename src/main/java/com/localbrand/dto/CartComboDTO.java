package com.localbrand.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartComboDTO {

    private Long idCartCombo;
    private Integer idCart;
    private Integer idCombo;
    private Integer quantity;

    private Integer idUser;
    private ComboDTO combo;
}