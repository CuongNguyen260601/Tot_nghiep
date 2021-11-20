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
public class ModuleRoleDTO {

    private Long idModule;

    private String nameModule;

    private List<RoleActionDTO> roleActionDTOS;
}
