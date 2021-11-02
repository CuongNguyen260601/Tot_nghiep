package com.localbrand.dto.request;

import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSizeRequestDTO {

    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_PRODUCT_DETAIL)
    private Long idProductDetail;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_ID_SIZE)
    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_SIZE)
    private Long idSize;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_QUANTITY)
    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_QUANTITY)
    private Integer quantity;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_PRICE)
    @Min(value = 10000,message = Notification.Product.Validate_Product_Request.VALIDATE_PRICE)
    private Float price;

    private Date dateCreate;

    @Min(value = 1, message = Notification.Product.Validate_Product_Request.VALIDATE_ID_SALE)
    private Integer idSale;

    @NotNull(message = Notification.Product.Validate_Product_Request.VALIDATE_STATUS_PRODUCT)
    private Integer idStatus;
}