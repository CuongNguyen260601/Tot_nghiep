package com.localbrand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {

    private Long idProductDetail;

    private Integer idProduct;

    private Integer idGender;

    private CategoryChildDTO categoryDTO;

    private ColorDTO colorDTO;

    private SizeDTO sizeDTO;

    private Float price;

    private Integer quantity;

    private Date dateCreate;

    private SaleDTO saleDTO;

    private String detailPhoto;

    private List<Integer> listTagDTO;

}
