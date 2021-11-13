package com.localbrand.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

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
    private AddressRequestDTO addressRequestDTO;
    private Integer idStatus;
    private String imageUser;

}
