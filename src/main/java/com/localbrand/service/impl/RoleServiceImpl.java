package com.localbrand.service.impl;

import com.localbrand.common.Action_Enum;
import com.localbrand.common.Module_Enum;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ModuleRoleDTO;
import com.localbrand.dto.request.RoleAcceptDTO;
import com.localbrand.entity.RoleDetail;
import com.localbrand.entity.RoleMapModule;
import com.localbrand.entity.User;
import com.localbrand.model_mapping.Impl.RoleDetailMapping;
import com.localbrand.repository.RoleDetailRepository;
import com.localbrand.repository.UserRepository;
import com.localbrand.service.RoleService;
import com.localbrand.utils.Role_Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDetailRepository roleDetailRepository;
    private final RoleDetailMapping roleDetailMapping;
    private final UserRepository userRepository;
    private final Role_Utils role_utils;

    @Override
    public ServiceResult<List<ModuleRoleDTO>> getListRole(HttpServletRequest request) {

        User user;

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.ROLE.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get role", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get role", null);
        }

        List<RoleMapModule> roleMapModules = this.roleDetailRepository.findAllRoleDetail();

        return new ServiceResult<>(HttpStatus.OK, "Get list role is success", this.roleDetailMapping.toResponse(roleMapModules));
    }

    @Override
    public ServiceResult<List<ModuleRoleDTO>> acceptRole(HttpServletRequest request, RoleAcceptDTO roleAcceptDTO) {
        User user;

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.ROLE.getModule(), Action_Enum.SAVE.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save role", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save role", null);
        }

        RoleDetail roleDetail = this.roleDetailRepository.findById(roleAcceptDTO.getIdRoleDetail()).orElse(null);

        if(Objects.isNull(roleDetail)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "Invalid role accept", null);
        }

        roleDetail.setAccept(roleAcceptDTO.getAccept());

        this.roleDetailRepository.save(roleDetail);

        List<RoleMapModule> roleMapModules = this.roleDetailRepository.findAllRoleDetail();

        return new ServiceResult<>(HttpStatus.OK, "Accept role is success", this.roleDetailMapping.toResponse(roleMapModules));
    }
}
