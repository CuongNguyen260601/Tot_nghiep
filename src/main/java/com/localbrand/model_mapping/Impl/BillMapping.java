package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.response.BillResponseDTO;
import com.localbrand.entity.Address;
import com.localbrand.entity.Bill;
import com.localbrand.entity.Voucher;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.AddressRepository;
import com.localbrand.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillMapping implements Mapping<BillResponseDTO, Bill> {

    private final AddressRepository addressRepository;
    private final AddressMapping addressMapping;
    private final VoucherRepository voucherRepository;
    private final VoucherMapping voucherMapping;

    @Override
    public BillResponseDTO toDto(Bill bill) {

        Address address = this.addressRepository.findById(bill.getIdBill()).orElse(null);

        Voucher voucher = this.voucherRepository.findById(bill.getIdVoucher().longValue()).orElse(null);

        return BillResponseDTO
                .builder()
                .idBill(bill.getIdBill())
                .idUser(bill.getIdUser())
                .address(this.addressMapping.toDto(address))
                .phoneUser(bill.getPhoneUser())
                .emailUser(bill.getEmailUser())
                .dateCreate(bill.getDateCreate())
                .dateSuccess(bill.getDateSuccess())
                .descriptionBill(bill.getDescriptionBill())
                .total(bill.getTotal())
                .deposit(bill.getDeposit())
                .payment(bill.getPayment())
                .transportFee(bill.getTransportFee())
                .voucher(this.voucherMapping.toDto(voucher))
                .build();
    }

    @Override
    public Bill toEntity(BillResponseDTO billResponseDTO) {
        return Bill
                .builder()
                .idBill(billResponseDTO.getIdBill())
                .idUser(billResponseDTO.getIdUser())
                .idAddress(billResponseDTO.getAddress().getIdAddress().intValue())
                .phoneUser(billResponseDTO.getPhoneUser())
                .emailUser(billResponseDTO.getEmailUser())
                .dateCreate(billResponseDTO.getDateCreate())
                .dateSuccess(billResponseDTO.getDateSuccess())
                .descriptionBill(billResponseDTO.getDescriptionBill())
                .total(billResponseDTO.getTotal())
                .deposit(billResponseDTO.getDeposit())
                .payment(billResponseDTO.getPayment())
                .transportFee(billResponseDTO.getTransportFee())
                .idVoucher(billResponseDTO.getVoucher().getIdVoucher().intValue())
                .idStatus(billResponseDTO.getIdStatus())
                .build();
    }

    public Bill toEntitySave(BillRequestDTO billRequestDTO){
        return Bill
                .builder()
                .idBill(billRequestDTO.getIdBill())
                .idUser(billRequestDTO.getIdUser())
                .phoneUser(billRequestDTO.getPhoneUser())
                .emailUser(billRequestDTO.getEmailUser())
                .dateCreate(billRequestDTO.getDateCreate())
                .dateSuccess(billRequestDTO.getDateSuccess())
                .descriptionBill(billRequestDTO.getDescriptionBill())
                .deposit(billRequestDTO.getDeposit())
                .payment(billRequestDTO.getPayment())
                .transportFee(billRequestDTO.getTransportFee())
                .idVoucher(billRequestDTO.getIdVoucher())
                .idStatus(billRequestDTO.getIdStatus())
                .build();
    }
}
