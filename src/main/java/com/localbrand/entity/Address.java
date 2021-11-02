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
@Table(name = "_Address")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idAddress", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAddress;

    @Column(name = "idProvince", nullable = false)
    private Integer idProvince;

    @Column(name = "idDistrict", nullable = false)
    private Integer idDistrict;

    @Column(name = "idCommune", nullable = false)
    private Integer idCommune;

    @Column(name = "detailAddress", nullable = false)
    private String detailAddress;

}
