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
@Table(name = "_Product_Sale")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idProductSale", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductSale;

    @Column(name = "idSale", nullable = false)
    private Integer idSale;

    @Column(name = "idProductDetail")
    private Integer idProductDetail;

    @Column(name = "dateStart", nullable = false)
    private Date dateStart;

    @Column(name = "dateEnd", nullable = false)
    private Date dateEnd;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
