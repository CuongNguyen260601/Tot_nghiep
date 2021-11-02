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
@Table(name = "_Color")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Color implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idColor", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idColor;

    @Column(name = "nameColor", nullable = false)
    private String nameColor;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

}
