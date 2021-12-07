package com.localbrand.dto.response;

import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.ColorDTO;
import com.localbrand.dto.SizeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailSaleResponseDTO extends ProductParentResponseDTO {

    private Long idProductDetail;

    private Integer idGender;

    private CategoryChildDTO categoryDTO;

    private ColorDTO colorDTO;

    private SizeDTO sizeDTO;

    private Float price;

    private Integer amount;

    private Date dateCreate;

    private String detailPhoto;

}
