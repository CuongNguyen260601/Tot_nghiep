package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.VoucherDTO;
import com.localbrand.entity.Voucher;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapping  implements Mapping<VoucherDTO, Voucher> {
    @Override
    public VoucherDTO toDto(Voucher voucher) {
        return VoucherDTO
                .builder()
                .idVoucher(voucher.getIdVoucher())
                .nameVoucher(voucher.getNameVoucher())
                .condition(voucher.getCondition())
                .discount(voucher.getDiscount())
                .idStatus(voucher.getIdStatus())
                .descriptionVoucher(voucher.getDescriptionVoucher())
                .codeVoucher(voucher.getCodeVoucher())
                .build();
    }

    @Override
    public Voucher toEntity(VoucherDTO voucherDTO) {
        return Voucher
                .builder()
                .idVoucher(voucherDTO.getIdVoucher())
                .nameVoucher(voucherDTO.getNameVoucher())
                .condition(voucherDTO.getCondition())
                .discount(voucherDTO.getDiscount())
                .idStatus(voucherDTO.getIdStatus())
                .descriptionVoucher(voucherDTO.getDescriptionVoucher())
                .build();
    }
}
