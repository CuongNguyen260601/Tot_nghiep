package com.localbrand.dto.response;

import com.localbrand.dto.ColorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductShowUserResponseDTO extends ProductParentResponseDTO{

    private List<Integer> listTag;

    private List<ColorDTO> listColor;

    private Float minPrice;

    private Float maxPrice;

    private Integer like;

    private Boolean isLike;

    private List<ProductChildResponseDTO> listProductSale;
}
