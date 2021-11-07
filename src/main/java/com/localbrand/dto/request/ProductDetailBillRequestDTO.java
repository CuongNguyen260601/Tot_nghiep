package com.localbrand.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailBillRequestDTO {

    private Long idProductDetail;
    private Integer quantity;
    private Integer idStatus;
}
