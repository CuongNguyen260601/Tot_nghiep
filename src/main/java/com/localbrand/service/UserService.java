package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ChangePasswordRequest;
import com.localbrand.dto.request.UserRequestDTO;
import com.localbrand.dto.request.UserUpdateRequestDTO;
import com.localbrand.dto.response.RefreshTokenDTO;
import com.localbrand.dto.response.RoleResponseDTO;
import com.localbrand.dto.response.UserResponseDTO;
import com.localbrand.dto.response.UserResponseSignupDTO;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    ServiceResult<UserResponseSignupDTO> singUp(HttpServletRequest request, UserRequestDTO userRequestDTO);

    ServiceResult<UserResponseDTO> updateProfile(HttpServletRequest request, UserUpdateRequestDTO userUpdateRequestDTO);

    ServiceResult<RefreshTokenDTO> refreshToken(HttpServletRequest request, String refreshToken);

    ServiceResult<?> logout(HttpServletRequest request);

    ServiceResult<UserResponseSignupDTO> signUpAccountEmployee(HttpServletRequest request, UserRequestDTO userRequestDTO);

    ServiceResult<RoleResponseDTO> getListRoleResponse(HttpServletRequest request, Optional<Long> idUser, Optional<Integer> idModule);

    ServiceResult<UserResponseDTO> changePassword(HttpServletRequest request, ChangePasswordRequest changePasswordRequest);

    ServiceResult<?> getNewPassword(String email);

}
