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
@Table(name = "_Combo_Tag")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idComboTag", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComboTag;

    @Column(name = "idCombo", nullable = false)
    private Integer idCombo;

    @Column(name = "idTag", nullable = false)
    private Integer idTag;

}
