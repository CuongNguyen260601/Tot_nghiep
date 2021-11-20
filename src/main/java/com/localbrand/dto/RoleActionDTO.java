package com.localbrand.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleActionDTO {

    private Integer idRole;

    private String nameRole;

    private List<ActionRoleDTO> actionRoleDTOS;
}
