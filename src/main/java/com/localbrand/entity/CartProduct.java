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
@Table(name = "_Cart_Product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCartProduct", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartProduct;

    @Column(name = "idCart", nullable = false)
    private Integer idCart;

    @Column(name = "idProductDetail", nullable = false)
    private Integer idProductDetail;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
