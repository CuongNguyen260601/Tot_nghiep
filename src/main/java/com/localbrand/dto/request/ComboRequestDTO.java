package com.localbrand.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboRequestDTO {

    @Min(value = 1, message = Notification.Combo.Validate_Combo.VALIDATE_ID)
    private Long idCombo;

    @NotBlank(message = Notification.Combo.Validate_Combo.VALIDATE_NAME)
    private String nameCombo;

    @NotNull(message = Notification.Combo.Validate_Combo.VALIDATE_PRICE)
    @Min(value = 0, message = Notification.Combo.Validate_Combo.VALIDATE_PRICE)
    private Float price;

    @NotNull(message = Notification.Combo.Validate_Combo.VALIDATE_STATUS)
    @Min(value = 1, message = Notification.Combo.Validate_Combo.VALIDATE_STATUS)
    @Max(value = 10, message = Notification.Combo.Validate_Combo.VALIDATE_STATUS)
    private Integer idStatus;

    private String descriptionCombo;

    private Date createAt;

    private String coverPhoto;

    private String frontPhoto;

    private String backPhoto;

    @NotNull(message = Notification.Combo.Validate_Combo.VALIDATE_QUANTITY)
    @Min(value = 0, message = Notification.Combo.Validate_Combo.VALIDATE_QUANTITY)
    private Integer quantity;

    @JsonProperty("comboDetail")
    private List<ComboDetailRequestDTO> comboDetailRequestDTO;
}
