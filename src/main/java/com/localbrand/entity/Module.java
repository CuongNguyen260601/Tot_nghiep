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
@Table(name = "_Module")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idModule", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModule;

    @Column(name = "nameModule", nullable = false)
    private String nameModule;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
