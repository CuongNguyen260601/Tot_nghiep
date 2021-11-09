package com.localbrand.dto.request;

import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_PRODUCT)
    private Long idProduct;

    @NotBlank(message = Notification.Product.Validate_Product_Request.VALIDATE_NAME_PRODUCT)
    private String nameProduct;

    private Date dateCreate;

    private Integer totalProduct;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_STATUS_PRODUCT)
    private Integer idStatus;

    private String descriptionProduct;

    @NotBlank(message = Notification.Product.Validate_Product_Request.VALIDATE_FRONT_PHOTO)
    private String frontPhoto;

    @NotBlank(message = Notification.Product.Validate_Product_Request.VALIDATE_BACK_PHOTO)
    private String backPhoto;

    @NotBlank(message = Notification.Product.Validate_Product_Request.VALIDATE_COVER_PHOTO)
    private String coverPhoto;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_PRODUCT_DETAIL)
    private ProductDetailRequestDTO detailInProduct;
}
