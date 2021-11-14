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
@Table(name = "_Product_Detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProductDetail", nullable = false)
    private Long idProductDetail;

    @Column(name = "idProduct", nullable = false)
    private Integer idProduct;

    @Column(name = "idGender", nullable = false)
    private Integer idGender;

    @Column(name = "idCategory", nullable = false)
    private Integer idCategory;

    @Column(name = "idColor", nullable = false)
    private Integer idColor;

    @Column(name = "idSize", nullable = false)
    private Integer idSize;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "dateCreate", nullable = false)
    private Date dateCreate;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

    @Column(name = "detailPhoto", nullable = false)
    private String detailPhoto;

}
