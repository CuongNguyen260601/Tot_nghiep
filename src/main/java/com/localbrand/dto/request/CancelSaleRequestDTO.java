package com.localbrand.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelSaleRequestDTO {

    private Long idSale;

    private List<ProductSaleCancelRequestDTO> listProductDetail;
}
