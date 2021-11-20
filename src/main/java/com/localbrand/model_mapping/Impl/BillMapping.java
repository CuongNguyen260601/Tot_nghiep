package com.localbrand.model_mapping.Impl;

import com.localbrand.common.Name_Status_Enum;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.response.BillProductResponseDTO;
import com.localbrand.dto.response.BillResponseDTO;
import com.localbrand.dto.response.BillResponseUserDTO;
import com.localbrand.entity.*;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.AddressRepository;
import com.localbrand.repository.BillProductRepository;
import com.localbrand.repository.UserRepository;
import com.localbrand.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BillMapping implements Mapping<BillResponseDTO, Bill> {

    private final AddressRepository addressRepository;
    private final AddressMapping addressMapping;
    private final VoucherRepository voucherRepository;
    private final VoucherMapping voucherMapping;
    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final BillProductRepository billProductRepository;
    private final BillProductMapping billProductMapping;

    @Override
    public BillResponseDTO toDto(Bill bill) {
        Address address = this.addressRepository.findById(bill.getIdAddress().longValue()).orElse(null);

        User user = this.userRepository.findById(bill.getIdUser().longValue()).orElse(null);

        BillResponseDTO billResponseDTO = BillResponseDTO
                .builder()
                .idBill(bill.getIdBill())
                .userResponseDTO(this.userMapping.toDto(user))
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
                .billType(bill.getBillType())
                .idStatus(bill.getIdStatus())
                .nameStatus(Name_Status_Enum.findByCode(bill.getIdStatus()).getName())
                .build();

        if(Objects.nonNull(bill.getIdVoucher())){
            Voucher voucher = this.voucherRepository.findById(bill.getIdVoucher().longValue()).orElse(null);

            if(Objects.nonNull(voucher)){
                billResponseDTO.setVoucher(this.voucherMapping.toDto(voucher));
            }
        }

        return billResponseDTO;
    }

    @Override
    public Bill toEntity(BillResponseDTO billResponseDTO) {
        return Bill
                .builder()
                .idBill(billResponseDTO.getIdBill())
                .idUser(billResponseDTO.getUserResponseDTO().getIdUser().intValue())
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
                .total(0F)
                .billType(billRequestDTO.getBillType())
                .build();
    }

    public BillResponseUserDTO toDtoResponse(Bill bill) {
        Address address = this.addressRepository.findById(bill.getIdAddress().longValue()).orElse(null);

        User user = this.userRepository.findById(bill.getIdUser().longValue()).orElse(null);

        BillResponseUserDTO billResponseUserDTO = new BillResponseUserDTO();
        billResponseUserDTO.setIdBill(bill.getIdBill());
        billResponseUserDTO.setUserResponseDTO(this.userMapping.toDto(user));
        billResponseUserDTO.setAddress(this.addressMapping.toDto(address));
        billResponseUserDTO.setPhoneUser(bill.getPhoneUser());
        billResponseUserDTO.setEmailUser(bill.getEmailUser());
        billResponseUserDTO.setDateCreate(bill.getDateCreate());
        billResponseUserDTO.setDateCreate(bill.getDateSuccess());
        billResponseUserDTO.setDescriptionBill(bill.getDescriptionBill());
        billResponseUserDTO.setTotal(bill.getTotal());
        billResponseUserDTO.setDeposit(bill.getDeposit());
        billResponseUserDTO.setPayment(bill.getPayment());
        billResponseUserDTO.setTransportFee(bill.getTransportFee());
        billResponseUserDTO.setBillType(bill.getBillType());
        billResponseUserDTO.setIdStatus(bill.getIdStatus());
        billResponseUserDTO.setNameStatus(Name_Status_Enum.findByCode(bill.getIdStatus()).getName());


        if(Objects.nonNull(bill.getIdVoucher())){
            Voucher voucher = this.voucherRepository.findById(bill.getIdVoucher().longValue()).orElse(null);

            if(Objects.nonNull(voucher)){
                billResponseUserDTO.setVoucher(this.voucherMapping.toDto(voucher));
            }
        }

        List<BillProduct> billProducts = this.billProductRepository.findAllByIdBillAndIdStatus(bill.getIdBill().intValue(), Status_Enum.EXISTS.getCode());

        List<BillProductResponseDTO> billProductResponseDTOS = billProducts.stream().map(this.billProductMapping::toDto).collect(Collectors.toList());

        billResponseUserDTO.setListBillProductDetail(billProductResponseDTOS);

        return billResponseUserDTO;
    }
}
