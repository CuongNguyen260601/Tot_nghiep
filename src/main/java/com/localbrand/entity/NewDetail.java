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
@Table(name = "_New_Detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idNewDetail", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNewDetail;

    @Column(name = "idNew", nullable = false)
    private Integer idNew;

    @Column(name = "titleNewDetail", nullable = false)
    private String titleNewDetail;

    @Column(name = "imageNewDetail")
    private String imageNewDetail;

    @Column(name = "content", nullable = false)
    private String content;

}
