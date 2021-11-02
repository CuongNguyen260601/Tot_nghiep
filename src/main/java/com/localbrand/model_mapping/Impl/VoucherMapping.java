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
                .discount(voucher.getDiscount())
                .dateStart(voucher.getDateStart())
                .dateEnd(voucher.getDateEnd())
                .idStatus(voucher.getIdStatus())
                .descriptionVoucher(voucher.getDescriptionVoucher())
                .build();
    }

    @Override
    public Voucher toEntity(VoucherDTO voucherDTO) {
        return Voucher
                .builder()
                .idVoucher(voucherDTO.getIdVoucher())
                .nameVoucher(voucherDTO.getNameVoucher())
                .discount(voucherDTO.getDiscount())
                .dateStart(voucherDTO.getDateStart())
                .dateEnd(voucherDTO.getDateEnd())
                .idStatus(voucherDTO.getIdStatus())
                .descriptionVoucher(voucherDTO.getDescriptionVoucher())
                .build();
    }
}
