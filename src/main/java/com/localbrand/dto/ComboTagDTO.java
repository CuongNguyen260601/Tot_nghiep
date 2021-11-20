package com.localbrand.dto;

import com.localbrand.entity.Tag;
import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboTagDTO {

    @Min(value = 1, message = Notification.ComboTag.Validate_ComboTag.VALIDATE_ID)
    private Long idComboTag;

    private Integer idCombo;

    private Tag tag;
}
