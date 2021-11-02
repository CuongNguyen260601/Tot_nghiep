package com.localbrand.dto.response;

import com.localbrand.dto.ColorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductColorResponseDTO {

    private ColorDTO color;

    private String detailPhoto;

    private List<ProductSizeResponseDTO> listSizeInColor;
}
