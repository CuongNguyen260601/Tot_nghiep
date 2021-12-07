package com.localbrand.dto.request;

import com.localbrand.dto.SaleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSaleRequestDTO {

    @NotNull(message = "Sale not null")
    private SaleDTO sale;

    private List<ProductSaleDetail> listProductDetail;

    private Date dateStart;

    private Date dateEnd;

}
