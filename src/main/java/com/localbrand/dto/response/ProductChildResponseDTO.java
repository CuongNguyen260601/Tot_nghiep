package com.localbrand.dto.response;

import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.ColorDTO;
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
public class ProductChildResponseDTO {

    private Long idProductDetail;

    private Integer idProduct;

    private String nameProduct;

    private Integer idGender;

    private CategoryChildDTO category;

    private ColorDTO color;

    private SizeDTO size;

    private Float price;

    private Integer quantity;

    private Date dateCreate;

    private String detailPhoto;

    private Integer idStatus;

    private SaleDTO saleDTO;

    private List<Integer> listTag;
}
