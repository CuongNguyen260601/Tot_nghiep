package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RoleMapModule {

    @Id
    private Long idModule;
    private String nameModule;
    private Long idAction;
    private String nameAction;
    private Long idRole;
    private String nameRole;
    private Long idRoleDetail;
    private Boolean accept;

}
