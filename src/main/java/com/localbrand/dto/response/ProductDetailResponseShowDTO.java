package com.localbrand.dto.response;

import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponseShowDTO {

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_ID_GENDER)
    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_GENDER)
    @Min(value = 3, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_GENDER)
    private Integer idGender;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_ID_CATEGORY)
    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_CATEGORY)
    private Integer idCategory;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_LIST_COLOR)
    @NotEmpty(message = Notification.Product.Validate_Product_Request.VALIDATE_LIST_COLOR)
    private List<ProductColorResponseShowDTO> listDetailColorRequest;

}
