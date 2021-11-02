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
public class CategoryChildDTO {

    @Min(value = 1, message = Notification.Category.Validate_Category.VALIDATE_ID)
    private Long idCategory;

    @NotNull(message = Notification.Category.Validate_Category.VALIDATE_ID_PARENT)
    @Min(value = 1, message = Notification.Category.Validate_Category.VALIDATE_ID_PARENT)
    private Integer parentId;

    @NotBlank(message = Notification.Category.Validate_Category.VALIDATE_NAME)
    private String nameCategory;

    @NotNull(message = Notification.Category.Validate_Category.VALIDATE_STATUS)
    @Min(value = 1, message = Notification.Category.Validate_Category.VALIDATE_STATUS)
    @Max(value = 10, message = Notification.Category.Validate_Category.VALIDATE_STATUS)
    private Integer idStatus;

}
