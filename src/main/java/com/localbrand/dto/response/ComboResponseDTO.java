package com.localbrand.dto.response;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.localbrand.entity.ComboTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboResponseDTO {
    private Long idCombo;

    private String nameCombo;

    private Float price;

    private Integer idStatus;

    private String descriptionCombo;

    private String coverPhoto;

    private String frontPhoto;

    private String backPhoto;

    private Date createAt;

    private List<ComboTag> listComboTag;

    private Integer quantity;

    @JsonProperty("comboDetail")
    private List<ComboDetailResponseDTO> comboDetailResponseDTO;
}
