package com.localbrand.dto.response;

import com.localbrand.dto.CategoryParentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizeResponseDTO {

    private Long idSize;

    private String nameSize;

    private CategoryParentDTO categoryParent;

    private Integer idStatus;
}
