package com.localbrand.dto.response;

import com.localbrand.dto.NewDetailDTO;
import com.localbrand.entity.User;
import com.localbrand.exception.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsResponseDTO {

    @Min(value = 1, message = Notification.News.Validate_News.VALIDATE_ID)
    private Long idNew;

    private String title;

    private String shortContent;

    private Date dateCreate;

    private Integer viewNews;

    private String author;

    private Integer idStatus;

    private String imageNew;

    private List<NewDetailDTO> listNewDetail;
}
