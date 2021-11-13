package com.localbrand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseSignupDTO extends UserResponseDTO{

    private String access_token;
    private String refresh_token;

}
