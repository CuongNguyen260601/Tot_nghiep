package com.localbrand.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboDTO {

    private Long idCombo;

    private String nameCombo;

    private Float discount;

    private Integer idStatus;

    private String descriptionCombo;

    List<ComboDetailDTO> listComboDetailDTO;
}
