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
public class UserUpdateRequestDTO {

    private Long idUser;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String phoneNumber;

    private String passwordUser;

    private Integer idGender;

    private Integer idAddress;

    private String imageUser;

    private AddressRequestDTO addressRequestDTO;
}
