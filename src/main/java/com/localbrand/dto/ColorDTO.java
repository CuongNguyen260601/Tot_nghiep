package com.localbrand.dto;

import com.localbrand.exception.Notification;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColorDTO {

    @Min(value = 1, message = Notification.Color.Validate_Color.VALIDATE_ID)
    private Long idColor;

    @NotBlank(message = Notification.Color.Validate_Color.VALIDATE_NAME)
    private String nameColor;

    @NotNull(message = Notification.Color.Validate_Color.VALIDATE_STATUS)
    @Min(value = 1,message = Notification.Color.Validate_Color.VALIDATE_STATUS)
    @Max(value = 10, message = Notification.Color.Validate_Color.VALIDATE_STATUS)
    private Integer idStatus;

}
