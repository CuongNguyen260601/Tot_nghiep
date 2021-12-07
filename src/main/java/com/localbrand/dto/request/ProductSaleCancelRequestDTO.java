package com.localbrand.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSaleCancelRequestDTO {

    private Long idProductSale;

    private Integer idStatus;
}
