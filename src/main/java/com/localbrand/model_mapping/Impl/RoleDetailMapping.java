package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.ActionRoleDTO;
import com.localbrand.dto.ModuleRoleDTO;
import com.localbrand.dto.RoleActionDTO;
import com.localbrand.entity.RoleMapModule;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class RoleDetailMapping implements Mapping<ModuleRoleDTO, RoleMapModule> {
    @Override
    public ModuleRoleDTO toDto(RoleMapModule roleMapModule) {
        return null;
    }

    @Override
    public RoleMapModule toEntity(ModuleRoleDTO moduleRoleDTO) {
        return null;
    }

    public List<ModuleRoleDTO> toResponse(List<RoleMapModule> roleMapModules){

        List<ModuleRoleDTO> moduleRoleDTOS = new ArrayList<>();

        roleMapModules.forEach(roleMapModule -> {

            AtomicBoolean isDuplicateModule = new AtomicBoolean(false);

            moduleRoleDTOS.forEach(moduleRoleDTO -> {

                if(moduleRoleDTO.getIdModule().equals(roleMapModule.getIdModule())){

                    AtomicBoolean isDuplicateRole = new AtomicBoolean(false);

                    moduleRoleDTO.getRoleActionDTOS().forEach(roleActionDTO -> {

                        if(roleActionDTO.getIdRole().equals(roleMapModule.getIdRole().intValue())){

                            AtomicBoolean isDuplicateAction = new AtomicBoolean(false);

                            roleActionDTO.getActionRoleDTOS().forEach( actionRoleDTO -> {

                                if(actionRoleDTO.getIdAction().equals(roleMapModule.getIdAction().intValue())){
                                    actionRoleDTO.setAccept(roleMapModule.getAccept());
                                    isDuplicateAction.set(true);
                                }

                            });

                            if(!isDuplicateAction.get()){
                                roleActionDTO.getActionRoleDTOS().add(ActionRoleDTO.builder()
                                                .idAction(roleMapModule.getIdAction().intValue())
                                                .nameAction(roleMapModule.getNameAction())
                                                .idRoleDetail(roleMapModule.getIdRoleDetail().intValue())
                                                .accept(roleMapModule.getAccept())
                                        .build());
                            }

                            isDuplicateRole.set(true);
                        }
                    });

                    if(!isDuplicateRole.get()){
                        List<ActionRoleDTO> actionRoleDTOS = new ArrayList<>();
                        actionRoleDTOS.add(
                                ActionRoleDTO.builder()
                                        .idAction(roleMapModule.getIdAction().intValue())
                                        .nameAction(roleMapModule.getNameAction())
                                        .idRoleDetail(roleMapModule.getIdRoleDetail().intValue())
                                        .accept(roleMapModule.getAccept())
                                        .build()
                        );

                        moduleRoleDTO.getRoleActionDTOS().add(RoleActionDTO.builder()
                                        .idRole(roleMapModule.getIdRole().intValue())
                                        .nameRole(roleMapModule.getNameRole())
                                        .actionRoleDTOS(actionRoleDTOS)
                                .build());
                    }

                    isDuplicateModule.set(true);
                }

            });

            if(!isDuplicateModule.get()){

                List<RoleActionDTO> roleActionDTOS = new ArrayList<>();

                List<ActionRoleDTO> actionRoleDTOS = new ArrayList<>();

                actionRoleDTOS.add(ActionRoleDTO.builder()
                                .idAction(roleMapModule.getIdAction().intValue())
                                .nameAction(roleMapModule.getNameAction())
                                .idRoleDetail(roleMapModule.getIdRoleDetail().intValue())
                                .accept(roleMapModule.getAccept())
                        .build());

                roleActionDTOS.add(RoleActionDTO.builder()
                                .idRole(roleMapModule.getIdRole().intValue())
                                .nameRole(roleMapModule.getNameRole())
                                .actionRoleDTOS(actionRoleDTOS)
                        .build());

                moduleRoleDTOS.add(ModuleRoleDTO.builder()
                                .idModule(roleMapModule.getIdModule())
                                .nameModule(roleMapModule.getNameModule())
                                .roleActionDTOS(roleActionDTOS)
                        .build());
            }
        });
        return moduleRoleDTOS;
    }
}
