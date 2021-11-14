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

    @Column(name = "emailUser")
    private String emailUser;

    @Column(name = "dateCreate", nullable = false)
    private Date dateCreate;

    @Column(name = "dateSuccess")
    private Date dateSuccess;

    @Column(name = "total", nullable = false)
    private Float total;

    @Column(name = "deposit", nullable = false)
    private Float deposit;

    @Column(name = "payment", nullable = false)
    private Float payment;

    @Column(name = "transportFee", nullable = false)
    private Float transportFee;

    @Column(name = "idVoucher")
    private Integer idVoucher;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

    @Column(name = "billType", nullable = false)
    private Integer billType;

    @Column(name = "descriptionBill")
    private String descriptionBill;

}
