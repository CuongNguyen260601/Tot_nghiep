package com.localbrand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionRoleDTO {

    private Integer idAction;

    private String nameAction;

    private Integer idRoleDetail;

    private Boolean accept;
}
