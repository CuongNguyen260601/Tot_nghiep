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
@Table(name = "_Product_Tag")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idProductTag", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductTag;

    @Column(name = "idProductDetail", nullable = false)
    private Integer idProductDetail;

    @Column(name = "idTag", nullable = false)
    private Integer idTag;

}
