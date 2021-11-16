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
@Table(name = "_Combo")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCombo", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCombo;

    @Column(name = "nameCombo", nullable = false)
    private String nameCombo;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "createAt", nullable = false)
    private Date createAt;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

    @Column(name = "descriptionCombo")
    private String descriptionCombo;

    @Column(name = "coverPhoto", nullable = false)
    private String coverPhoto;

    @Column(name = "frontPhoto", nullable = false)
    private String frontPhoto;

    @Column(name = "backPhoto", nullable = false)
    private String backPhoto;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
