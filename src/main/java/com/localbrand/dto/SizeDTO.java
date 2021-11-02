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
public class SizeDTO {

    @Min(value = 1, message = Notification.Size.Validate_Size.VALIDATE_ID)
    private Long idSize;

    @NotBlank(message = Notification.Size.Validate_Size.VALIDATE_NAME)
    private String nameSize;

    @NotNull(message = Notification.Size.Validate_Size.VALIDATE_ID_CATEGORY)
    @Min(value = 1, message = Notification.Size.Validate_Size.VALIDATE_ID_CATEGORY)
    private Integer idCategory;

    @NotNull(message = Notification.Size.Validate_Size.VALIDATE_STATUS)
    @Min(value = 1,message = Notification.Size.Validate_Size.VALIDATE_STATUS)
    @Max(value = 10, message = Notification.Size.Validate_Size.VALIDATE_STATUS)
    private Integer idStatus;

}
