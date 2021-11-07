package com.localbrand.dto.response;

import com.localbrand.dto.ProductDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillProductResponseDTO {

    private Long idBillProduct;

    private Integer idBill;

    private ProductDetailUserDTO productDetailDTO;

    private Integer quantity;

    private Float price;

    private Integer idStatus;

}
