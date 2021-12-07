package com.localbrand.dto.response;

import com.localbrand.dto.SaleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSaleListResponseDTO {

    private SaleDTO sale;

    private ProductDetailSaleResponseDTO productDetail;
}
