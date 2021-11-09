package com.localbrand.dto.response;

import com.localbrand.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long idUser;

    private String idChat;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String email;

    private String phoneNumber;

    private String passwordUser;

    private Integer idRole;

    private Integer idGender;

    private AddressDTO addressDTO;

    private Integer idStatus;

    private String imageUser;

    private String accessToken;

    private String refreshToken;

}
