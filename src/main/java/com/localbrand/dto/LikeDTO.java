package com.localbrand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDTO {

    private Integer idUser;
    private Integer idProduct;
    private Integer idCombo;
    private Boolean isLike;
}
