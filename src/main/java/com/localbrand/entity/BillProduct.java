package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_Bill_Product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idBillProduct", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBillProduct;

    @Column(name = "idBill", nullable = false)
    private Integer idBill;

    @Column(name = "idProductDetail", nullable = false)
    private Integer idProductDetail;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
