package com.localbrand.dto.response;

import com.localbrand.dto.request.ProductDetailRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long idProduct;

    private String nameProduct;

    private Date dateCreate;

    private Integer totalProduct;

    private Integer idStatus;

    private String descriptionProduct;

    String frontPhoto;

    String backPhoto;

    String coverPhoto;

    private ProductDetailResponseDTO detailInProduct;
}
