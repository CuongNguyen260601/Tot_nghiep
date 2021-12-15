package com.localbrand.dto.response;

import com.localbrand.dto.GenderDTO;
import com.localbrand.dto.request.AddressRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long idUser;

    private String idChat;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String email;

    private String phoneNumber;

    private GenderDTO genderDTO;

    private AddressRequestDTO addressDTO;

    private Integer idStatus;

    private String imageUser;

    private Integer idCart;

    private Integer idRole;

}
