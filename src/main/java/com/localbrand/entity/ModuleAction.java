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
@Table(name = "_Module_Action")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idModuleAction", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModuleAction;

    @Column(name = "idModule", nullable = false)
    private Integer idModule;

    @Column(name = "idAction", nullable = false)
    private Integer idAction;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
