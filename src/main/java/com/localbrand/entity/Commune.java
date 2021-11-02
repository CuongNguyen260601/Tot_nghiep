package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_Commune")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commune implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCommune", nullable = false)
    private Integer idCommune;

    @Column(name = "nameCommune", nullable = false)
    private String nameCommune;

    @Column(name = "prefixCommune", nullable = false)
    private String prefixCommune;

    @Column(name = "idDistrict", nullable = false)
    private Integer idDistrict;

    @Column(name = "idProvince", nullable = false)
    private Integer idProvince;

}
