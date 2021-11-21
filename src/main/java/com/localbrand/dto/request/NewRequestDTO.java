package com.localbrand.dto.request;

import com.localbrand.dto.NewDetailDTO;
import com.localbrand.exception.Notification;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewRequestDTO {
    @Min(value = 1, message = Notification.News.Validate_News.VALIDATE_ID)
    private Long idNew;

    @NotBlank(message = Notification.News.Validate_News.VALIDATE_TITLE)
    private String title;

    @NotBlank(message = Notification.News.Validate_News.VALIDATE_SHORT_CONTENT)
    private String shortContent;

    private Date dateCreate;

    private Integer viewNews;

    @Min(value = 1, message = Notification.News.Validate_News.VALIDATE_ID_USER)
    private Integer idUser;

    @Min(value = 1, message = Notification.News.Validate_News.VALIDATE_STATUS)
    private Integer idStatus;

    @NotBlank(message = Notification.News.Validate_News.VALIDATE_IMAGE_NEW)
    private String imageNew;

    private List<NewDetailDTO> listNewDetail;
}
