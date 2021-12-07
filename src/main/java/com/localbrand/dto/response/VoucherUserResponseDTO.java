package com.localbrand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class VoucherUserResponseDTO {

    @Id
    private Long idVoucherUser;

    private Long idVoucher;

    private String nameVoucher;

    private Float condition;

    private Float discount;

    private String descriptionVoucher;

    private String codeVoucher;

    private Integer idUser;

    private Integer quantity;
}
