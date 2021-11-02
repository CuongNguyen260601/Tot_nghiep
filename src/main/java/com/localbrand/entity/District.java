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
@Table(name = "_District")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idDistrict", nullable = false)
    private Integer idDistrict;

    @Column(name = "nameDistrict", nullable = false)
    private String nameDistrict;

    @Column(name = "prefixDistrict")
    private String prefixDistrict;

    @Column(name = "idProvince", nullable = false)
    private Integer idProvince;

}
