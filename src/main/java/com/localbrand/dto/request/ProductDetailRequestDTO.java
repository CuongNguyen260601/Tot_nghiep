package com.localbrand.dto.request;

import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailRequestDTO {

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_ID_GENDER)
    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_GENDER)
    @Min(value = 3, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_GENDER)
    private Integer idGender;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_ID_CATEGORY)
    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_CATEGORY)
    private Integer idCategory;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_LIST_COLOR)
    @NotEmpty(message = Notification.Product.Validate_Product_Request.VALIDATE_LIST_COLOR)
    private List<ProductColorRequestDTO> listDetailColorRequest;

}
