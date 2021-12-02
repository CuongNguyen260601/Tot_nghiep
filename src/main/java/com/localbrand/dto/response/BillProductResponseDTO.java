package com.localbrand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillProductResponseDTO {

    private Long idBillProduct;

    private Integer idBill;

    private Integer quantity;

    private Float price;

    private Integer idStatus;

    private ProductChildResponseDTO productChildResponseDTO;


}
