package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_Role_Detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idRoleDetail", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoleDetail;

    @Column(name = "idModuleAction", nullable = false)
    private Integer idModuleAction;

    @Column(name = "idRole", nullable = false)
    private Integer idRole;

    @Column(name = "accept", nullable = false)
    private Boolean accept;

}
