package com.localbrand.dto.response;


import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.ColorDTO;
import com.localbrand.dto.SaleDTO;
import com.localbrand.dto.SizeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailUserDTO extends ProductParentResponseDTO{

    private Long idProductDetail;

    private Integer idGender;

    private CategoryChildDTO categoryDTO;

    private ColorDTO colorDTO;

    private SizeDTO sizeDTO;

    private Float price;

    private Integer amount;

    private Date dateCreate;

    private String detailPhoto;

    private SaleDTO saleDTO;

    private List<Integer> listTag;

    private Integer like;

}
