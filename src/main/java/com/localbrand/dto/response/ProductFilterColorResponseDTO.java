package com.localbrand.dto.response;

import com.localbrand.dto.ColorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterColorResponseDTO extends ProductParentResponseDTO {

    private ColorDTO colorDTO;

    private String photoByColor;

    private Integer amount;

    private Float minPrice;

    private Float maxPrice;

    private List<SizeAndTagInProductShowUser> sizeAndTag;

    private Integer like;
}
