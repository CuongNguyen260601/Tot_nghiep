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
@Table(name = "_Voucher")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idVoucher", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoucher;

    @Column(name = "codeVoucher", nullable = false)
    private String codeVoucher;

    @Column(name = "nameVoucher", nullable = false)
    private String nameVoucher;

    @Column(name = "discount", nullable = false)
    private Float discount;

    @Column(name = "dateStart", nullable = false)
    private Date dateStart;

    @Column(name = "dateEnd", nullable = false)
    private Date dateEnd;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

    @Column(name = "descriptionVoucher")
    private String descriptionVoucher;

}
