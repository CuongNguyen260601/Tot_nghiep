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
@Table(name = "_Cart")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCart", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCart;

    @Column(name = "idUser", nullable = false)
    private Integer idUser;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
