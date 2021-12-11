package com.localbrand.dto.response;

import com.localbrand.dto.SaleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSaleResponseDTO {

    private SaleDTO saleDTO;

    private List<ProductChildResponseDTO> lstProductChild;

    private Date dateStart;

    private Date dateEnd;

}
