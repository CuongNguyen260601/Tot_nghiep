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
@Table(name = "_Size")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Size implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idSize", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSize;

    @Column(name = "nameSize", nullable = false)
    private String nameSize;

    @Column(name = "idCategory", nullable = false)
    private Integer idCategory;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
