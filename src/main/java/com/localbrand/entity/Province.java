package com.localbrand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "_Province")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idProvince", nullable = false)
    private Integer idProvince;

    @Column(name = "nameProvince", nullable = false)
    private String nameProvince;

    @Column(name = "codeProvince", nullable = false)
    private String codeProvince;

}
