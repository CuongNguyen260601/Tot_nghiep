package com.localbrand.dto.response;

import com.localbrand.dto.SaleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSaleResponseDTO {

    private SaleDTO saleDTO;

    private List<ProductDetailSaleResponseDTO> productDetailSaleResponseDTOS;

}
