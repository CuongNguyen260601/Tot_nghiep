package com.localbrand.dto.response;

import com.localbrand.dto.CategoryChildDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponseDTO {

    private Integer idGender;

    private CategoryChildDTO category;

    List<ProductColorResponseDTO> listDetailColorResponse;

}
