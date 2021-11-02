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
@Table(name = "_Status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idStatus", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStatus;

    @Column(name = "nameStatus", nullable = false)
    private String nameStatus;

}
