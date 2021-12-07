package com.localbrand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillComboResponseDTO {

    private Long idBillCombo;

    private Integer idBill;

    private Integer quantity;

    private Float price;

    private Integer idStatus;

    private ComboResponseDTO comboResponseDTO;
}
