package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ModuleRoleDTO;
import com.localbrand.dto.request.ChangePasswordRequest;
import com.localbrand.dto.request.RoleAcceptDTO;
import com.localbrand.dto.request.UserRequestDTO;
import com.localbrand.dto.request.UserUpdateRequestDTO;
import com.localbrand.dto.response.RefreshTokenDTO;
import com.localbrand.dto.response.RoleResponseDTO;
import com.localbrand.dto.response.UserResponseDTO;
import com.localbrand.dto.response.UserResponseSignupDTO;
import com.localbrand.service.RoleService;
import com.localbrand.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(Interface_API.Cors.CORS)
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @PostMapping(Interface_API.API.Auth.SIGN_UP)
    public ResponseEntity<ServiceResult<UserResponseSignupDTO>> signUp(HttpServletRequest request, @Valid @RequestBody UserRequestDTO userRequestDTO){
        ServiceResult<UserResponseSignupDTO> result = this.userService.singUp(request,userRequestDTO);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping(Interface_API.API.Auth.REFRESH_TOKEN)
    public ResponseEntity<ServiceResult<RefreshTokenDTO>> refreshToken(HttpServletRequest request,@RequestParam String refreshToken){
        ServiceResult<RefreshTokenDTO> result = this.userService.refreshToken(request, refreshToken);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping(Interface_API.API.Auth.LOG_OUT)
    public ResponseEntity<ServiceResult<?>> logout(HttpServletRequest request){
        ServiceResult<?> result = this.userService.logout(request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping(Interface_API.API.Auth.UPDATE_PROFILE)
    public ResponseEntity<ServiceResult<UserResponseDTO>> updateProfile(HttpServletRequest request,@Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO){
        ServiceResult<UserResponseDTO> result = this.userService.updateProfile(request, userUpdateRequestDTO);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping(Interface_API.API.Auth.SIGN_UP_ACCOUNT_EMPLOYEE)
    public ResponseEntity<ServiceResult<UserResponseSignupDTO>> signUpAccountEmployee(HttpServletRequest request, @Valid @RequestBody UserRequestDTO userRequestDTO){
        ServiceResult<UserResponseSignupDTO> result = this.userService.signUpAccountEmployee(request, userRequestDTO);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping(Interface_API.API.Auth.GET_LIST_ROLE_DETAIL)
    public ResponseEntity<ServiceResult<RoleResponseDTO>> getListRole (HttpServletRequest request, @RequestParam Optional<Long> idUser, @RequestParam Optional<Integer> idModule){
        ServiceResult<RoleResponseDTO> result = this.userService.getListRoleResponse(request, idUser, idModule);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping(Interface_API.API.Auth.GET_ALL_ROLE_DETAIL)
    public ResponseEntity<ServiceResult<List<ModuleRoleDTO>>> getAllListRole (HttpServletRequest request){
        ServiceResult<List<ModuleRoleDTO>> result = this.roleService.getListRole(request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping(Interface_API.API.Auth.ACCEPT_ROLE)
    public ResponseEntity<ServiceResult<List<ModuleRoleDTO>>> acceptRole (HttpServletRequest request, @Valid @RequestBody RoleAcceptDTO roleAcceptDTO){
        ServiceResult<List<ModuleRoleDTO>> result = this.roleService.acceptRole(request, roleAcceptDTO);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping(Interface_API.API.Auth.CHANGE_PASSWORD)
    public ResponseEntity<ServiceResult<UserResponseDTO>> changePassword (HttpServletRequest request, @Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        ServiceResult<UserResponseDTO> result = this.userService.changePassword(request, changePasswordRequest);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping(Interface_API.API.Auth.GET_NEW_PASSWORD)
    public ResponseEntity<ServiceResult<?>> getNewPassword (@RequestParam String email){
        ServiceResult<?> result = this.userService.getNewPassword(email);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
