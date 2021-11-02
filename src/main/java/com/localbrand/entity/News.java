package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_News")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idNew", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNew;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "shortContent", nullable = false)
    private String shortContent;

    @Column(name = "dateCreate", nullable = false)
    private Date dateCreate;

    @Column(name = "viewNews", nullable = false)
    private Integer viewNews;

    @Column(name = "idUser", nullable = false)
    private Integer idUser;

    @Column(name = "idStatus", nullable = false)
    private Integer idStatus;

    @Column(name = "imageNew")
    private String imageNew;

}
