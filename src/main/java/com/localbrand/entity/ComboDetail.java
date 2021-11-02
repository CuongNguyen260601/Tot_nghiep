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
@Table(name = "_Combo_Detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idComboDetail", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComboDetail;

    @Column(name = "idCombo", nullable = false)
    private Integer idCombo;

    @Column(name = "idProductDetail", nullable = false)
    private Integer idProductDetail;

}
