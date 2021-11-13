package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.UserRequestDTO;
import com.localbrand.dto.response.RefreshTokenDTO;
import com.localbrand.dto.response.UserResponseSignupDTO;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    ServiceResult<UserResponseSignupDTO> singUp(HttpServletRequest request, UserRequestDTO userRequestDTO);

    ServiceResult<RefreshTokenDTO> refreshToken(HttpServletRequest request, String refreshToken);

    ServiceResult<?> logout(HttpServletRequest request);

}
