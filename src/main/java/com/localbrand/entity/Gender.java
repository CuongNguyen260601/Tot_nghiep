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
@Table(name = "_Gender")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gender implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idGender", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGender;

    @Column(name = "nameGender", nullable = false)
    private String nameGender;

}
