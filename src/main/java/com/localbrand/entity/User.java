package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_User")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idUser", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "idChat", nullable = false)
    private String idChat;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "passwordUser", nullable = false)
    private String passwordUser;

    @Column(name = "idRole", nullable = false)
    private Integer idRole;

    @Column(name = "idGender", nullable = false)
    private Integer idGender;

    @Column(name = "idAddress", nullable = false)
    private Integer idAddress;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

    @Column(name = "imageUser")
    private String imageUser;

}
