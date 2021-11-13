package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.UserRequestDTO;
import com.localbrand.dto.response.RefreshTokenDTO;
import com.localbrand.dto.response.UserResponseSignupDTO;
import com.localbrand.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(Interface_API.Cors.CORS)
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

    @GetMapping("/admin/helu")
    public ResponseEntity<String> test(HttpServletRequest request){
        return ResponseEntity.ok().body(request.getAttribute("USER_NAME").toString());
    }

}
