package com.localbrand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewDetailDTO {

    private Long idNewDetail;

    private Integer idNew;

    private String titleNewDetail;

    private String imageNewDetail;

    private String content;
}
