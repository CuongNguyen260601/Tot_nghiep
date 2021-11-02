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
@Table(name = "_Product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idProduct", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column(name = "nameProduct", nullable = false)
    private String nameProduct;

    @Column(name = "dateCreate", nullable = false)
    private Date dateCreate;

    @Column(name = "totalProduct", nullable = false)
    private Integer totalProduct;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

    @Column(name = "descriptionProduct")
    private String descriptionProduct;

    @Column(name = "coverPhoto", nullable = false)
    private String coverPhoto;

    @Column(name = "frontPhoto", nullable = false)
    private String frontPhoto;

    @Column(name = "backPhoto", nullable = false)
    private String backPhoto;

}
