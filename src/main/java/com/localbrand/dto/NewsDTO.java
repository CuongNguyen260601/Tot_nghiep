package com.localbrand.dto;

import com.localbrand.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.sql.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDTO {

    private Long idNew;

    private String title;

    private String shortContent;

    private Date dateCreate;

    private Integer viewNews;

    private User user;

    private Integer idStatus;

    private String imageNew;

    private List<NewDetailDTO> listNewDetail;
}
