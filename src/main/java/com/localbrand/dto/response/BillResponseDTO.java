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

    private UserResponseDTO userResponseDTO;

    private AddressDTO address;

    private String phoneUser;

    private String emailUser;

    private Date dateCreate;

    private Date dateSuccess;

    private String descriptionBill;

    private Float total;

    private Float deposit;

    private Float payment;

    private Float transportFee;

    private VoucherDTO voucher;

    private Integer idStatus;

    private String nameStatus;

    private Integer billType;
}
