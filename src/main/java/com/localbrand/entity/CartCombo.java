package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_Cart_Combo")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartCombo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCartCombo", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartCombo;

    @Column(name = "idCart", nullable = false)
    private Integer idCart;

    @Column(name = "idCombo", nullable = false)
    private Integer idCombo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartCombo cartCombo = (CartCombo) o;
        return Objects.equals(idCartCombo, cartCombo.idCartCombo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCartCombo, idCart, idCombo);
    }

}
