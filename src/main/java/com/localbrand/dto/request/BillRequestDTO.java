package com.localbrand.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillRequestDTO {

    private Long idBill;

    private Integer idUser;

    private Integer idAddress;

    private String phoneUser;

    private String emailUser;

    private Date dateCreate;

    private Date dateSuccess;

    private String descriptionBill;

    private Float total;

    private Float deposit;

    private Float payment;

    private Float transportFee;

    private Integer idVoucher;

    private Integer idStatus;

    private List<ProductDetailBillRequestDTO> listProductDetail;

    private List<ComboBillRequestDTO> listCombo;

    private AddressRequestDTO addressRequestDTO;

    private Integer billType;

    private String codeVoucher;
}
