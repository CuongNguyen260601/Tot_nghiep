package com.localbrand.dto.request;


import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboDetailRequestDTO {

    private Long idComboDetail;

    private Integer idCombo;

    @NotNull(message = Notification.ComboDetail.Validate_ComboDetail.VALIDATE_ID_PRODUCT_DETAIL)
    private Integer idProductDetail;

}
