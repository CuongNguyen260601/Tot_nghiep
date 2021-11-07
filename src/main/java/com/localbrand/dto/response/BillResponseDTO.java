package com.localbrand.dto.response;

import com.localbrand.dto.AddressDTO;
import com.localbrand.dto.VoucherDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponseDTO {

    private Long idBill;

    private Integer idUser;

    private AddressDTO address;

    private String phoneUser;

    private String emailUser;

    private Date dateCreate;

    private Date dateSuccess;

    private String descriptionBill;

    private Integer total;

    private Float deposit;

    private Float payment;

    private Float transportFee;

    private VoucherDTO voucher;

    private Integer idStatus;
}