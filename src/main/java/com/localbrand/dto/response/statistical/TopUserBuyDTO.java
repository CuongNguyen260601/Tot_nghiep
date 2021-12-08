package com.localbrand.dto.response.statistical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopUserBuyDTO implements Serializable {

    @Id
    private int id;

    private String firthName;

    private String lastName;

    private double totalBuy;

    private long quantityBill;

}
