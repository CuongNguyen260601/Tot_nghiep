package com.localbrand.dto.request;

import com.localbrand.dto.ColorDTO;
import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductColorRequestDTO {

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_ID_COLOR)
    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_COLOR)
    private Integer idColor;

    @NotBlank(message = Notification.Product.Validate_Product_Request.VALIDATE_DETAIL_PHOTO)
    private String detailPhoto;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_LIST_SIZE_IN_COLOR)
    @NotEmpty(message = Notification.Product.Validate_Product_Request.VALIDATE_LIST_SIZE_IN_COLOR)
    private List<ProductSizeRequestDTO> listSizeInColor;
}
