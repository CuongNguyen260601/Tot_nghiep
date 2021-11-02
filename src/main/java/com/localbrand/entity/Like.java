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
@Table(name = "_Like")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idLike", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLike;

    @Column(name = "idProduct")
    private Integer idProduct;

    @Column(name = "idCombo")
    private Integer idCombo;

    @Column(name = "likeProduct")
    private Boolean likeProduct;

    @Column(name = "idUser", nullable = false)
    private Integer idUser;

}
