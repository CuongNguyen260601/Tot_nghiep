package com.localbrand.dto;

import com.localbrand.exception.Notification;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryParentDTO {

    @Min(value = 1, message = Notification.Category.Validate_Category.VALIDATE_ID)
    private Long idCategory;

    @NotBlank(message = Notification.Category.Validate_Category.VALIDATE_NAME)
    private String nameCategory;

    @NotNull(message = Notification.Category.Validate_Category.VALIDATE_STATUS)
    @Min(value = 1, message = Notification.Category.Validate_Category.VALIDATE_STATUS)
    @Max(value = 10, message = Notification.Category.Validate_Category.VALIDATE_STATUS)
    private Integer idStatus;

    private List<CategoryChildDTO> listCategoryChildDTO;
}
