package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_Jwt")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jwt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idJwt", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJwt;

    @Column(name = "idUser", nullable = false)
    private Integer idUser;

    @Column(name = "jwtToken", nullable = false)
    private String jwtToken;

    @Column(name = "endTime", nullable = false)
    private Date endTime;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

}
