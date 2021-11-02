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
@Table(name = "_Category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCategory", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;

    @Column(name = "parentId")
    private Integer parentId;

    @Column(name = "nameCategory", nullable = false)
    private String nameCategory;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
