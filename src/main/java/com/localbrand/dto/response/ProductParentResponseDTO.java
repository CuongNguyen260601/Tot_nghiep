package com.localbrand.dto.response;

import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.CategoryParentDTO;
import com.localbrand.dto.GenderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductParentResponseDTO {

    private Long idProduct;

    private String nameProduct;

    private Date dateCreate;

    private Integer totalProduct;

    private Integer idStatus;

    private String descriptionProduct;

    private String coverPhoto;

    private String frontPhoto;

    private String backPhoto;

    private CategoryParentDTO categoryParentDTO;

    private CategoryChildDTO categoryChildDTO;

    private GenderDTO genderDTO;
    
}
