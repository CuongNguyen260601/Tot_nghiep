package com.localbrand.dto.response.statistical;

import com.localbrand.dto.NewDetailDTO;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopProductHotDTO implements Serializable {

    @Id
    private int idProductDetail;

    private String nameProduct;

    private long quantityProduct;

}
