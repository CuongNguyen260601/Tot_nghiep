package com.localbrand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseShowAdminDTO{

    private Integer idCategoryParent;

    private ProductResponseShowDTO productResponseShowDTO;
}
