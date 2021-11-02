package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_Bill")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idBill", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBill;

    @Column(name = "idUser", nullable = false)
    private Integer idUser;

    @Column(name = "idAddress", nullable = false)
    private Integer idAddress;

    @Column(name = "phoneUser", nullable = false)
    private String phoneUser;

    @Column(name = "emailUser", nullable = false)
    private String emailUser;

    @Column(name = "dateCreate", nullable = false)
    private Date dateCreate;

    @Column(name = "dateSuccess", nullable = false)
    private Date dateSuccess;

    @Column(name = "descriptionBill", nullable = false)
    private String descriptionBill;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "deposit", nullable = false)
    private BigDecimal deposit;

    @Column(name = "payment", nullable = false)
    private BigDecimal payment;

    @Column(name = "transportFee", nullable = false)
    private BigDecimal transportFee;

    @Column(name = "idVoucher")
    private Integer idVoucher;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
