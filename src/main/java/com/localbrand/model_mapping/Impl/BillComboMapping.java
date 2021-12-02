package com.localbrand.model_mapping.Impl;

import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.request.ComboBillRequestDTO;
import com.localbrand.dto.request.ProductDetailBillRequestDTO;
import com.localbrand.dto.response.BillComboResponseDTO;
import com.localbrand.dto.response.BillProductResponseDTO;
import com.localbrand.dto.response.ComboDetailResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BillComboMapping implements Mapping<BillComboResponseDTO, BillCombo> {

    private final ComboRepository comboRepository;
    private final ComboMapping comboMapping;
    private final BillComboRepository billComboRepository;

    private final ComboDetailMapping comboDetailMapping;
    private final ComboDetailRepository comboDetailRepository;

    @Override
    public BillComboResponseDTO toDto(BillCombo billCombo) {

        Combo combo = this.comboRepository.findById(billCombo.getIdCombo().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.COMBO_IS_NULL));

        List<ComboDetail> listComboDetail = comboDetailRepository.findAllByIdCombo(combo.getIdCombo().intValue());

        return BillComboResponseDTO
                .builder()
                .idBillCombo(billCombo.getIdBillCombo())
                .idBill(billCombo.getIdBill())
                .comboResponseDTO(this.comboMapping.toDto(combo,null, listComboDetail.stream().map(this.comboDetailMapping::toDto).collect(Collectors.toList())))
                .quantity(billCombo.getQuantity())
                .price(billCombo.getPrice())
                .idStatus(billCombo.getIdStatus())
                .build();
    }

    @Override
    public BillCombo toEntity(BillComboResponseDTO billProductResponseDTO) {
        return null;
    }

    public List<BillCombo> toListCombo(Bill bill, BillRequestDTO billRequestDTO) {

        List<BillCombo> lstBillCombo = new ArrayList<>();

        for (ComboBillRequestDTO detailBillRequestDTO : billRequestDTO.getListCombo()) {

            BillCombo billCombo = this.billComboRepository.findFirstByIdBillAndAndIdCombo(bill.getIdBill().intValue(), detailBillRequestDTO.getIdCombo().intValue());

            if (Objects.nonNull(billCombo)) {
                billCombo.setQuantity(detailBillRequestDTO.getQuantity());
                billCombo.setIdStatus(detailBillRequestDTO.getIdStatus());
                lstBillCombo.add(billCombo);
            } else {
                Combo combo = this.comboRepository.findById(detailBillRequestDTO.getIdCombo()).orElse(null);

                if (Objects.nonNull(combo)) {

                    BillCombo billComboNull = BillCombo
                            .builder()
                            .idBill(bill.getIdBill().intValue())
                            .idCombo(combo.getIdCombo().intValue())
                            .quantity(detailBillRequestDTO.getQuantity())
                            .price(combo.getPrice())
                            .idStatus(detailBillRequestDTO.getIdStatus())
                            .build();
                    lstBillCombo.add(billComboNull);
                }
            }
        }
        return lstBillCombo;
    }

}
