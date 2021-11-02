package com.localbrand.dto.response;

import com.localbrand.dto.SaleDTO;
import com.localbrand.dto.SizeDTO;
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
public class ProductSizeResponseDTO {

    private Long idProductDetail;

    private SizeDTO size;

    private Integer quantity;

    private Float price;

    private SaleDTO sale;

    private Date dateCreate;

    private Integer idStatus;

    private List<Integer> listTag;
}
