package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ModuleRoleDTO;
import com.localbrand.dto.request.RoleAcceptDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RoleService {

    ServiceResult<List<ModuleRoleDTO>> getListRole(HttpServletRequest request);

    ServiceResult<List<ModuleRoleDTO>> acceptRole(HttpServletRequest request, RoleAcceptDTO roleAcceptDTO);
}
