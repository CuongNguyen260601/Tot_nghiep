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
@Table(name = "_Sale")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idSale", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSale;

    @Column(name = "nameSale", nullable = false)
    private String nameSale;

    @Column(name = "discount", nullable = false)
    private Float discount;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

    @Column(name = "descriptionSale")
    private String descriptionSale;

}
