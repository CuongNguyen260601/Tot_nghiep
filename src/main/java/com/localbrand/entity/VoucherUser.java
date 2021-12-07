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
@Table(name = "_Voucher_User")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idVoucherUser", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoucherUser;

    @Column(name = "idVoucher", nullable = false)
    private Integer idVoucher;

    @Column(name = "idUser", nullable = false)
    private Integer idUser;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
