package com.localbrand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponseUserDTO extends BillResponseDTO{

    List<BillProductResponseDTO> listBillProductDetail;

    List<BillComboResponseDTO> listBillComboDetail;
}
