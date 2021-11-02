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
@Table(name = "_Action")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idAction", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAction;

    @Column(name = "nameAction", nullable = false)
    private String nameAction;

}
