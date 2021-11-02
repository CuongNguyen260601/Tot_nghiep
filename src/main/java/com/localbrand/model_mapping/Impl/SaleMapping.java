package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.SaleDTO;
import com.localbrand.entity.Sale;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class SaleMapping implements Mapping<SaleDTO, Sale>{
    @Override
    public SaleDTO toDto(Sale sale) {
        return SaleDTO
                .builder()
                .idSale(sale.getIdSale())
                .nameSale(sale.getNameSale())
                .discount(sale.getDiscount())
                .idStatus(sale.getIdStatus())
                .descriptionSale(sale.getDescriptionSale())
                .build();
    }

    @Override
    public Sale toEntity(SaleDTO saleDTO) {
        return Sale
                .builder()
                .idSale(saleDTO.getIdSale())
                .nameSale(saleDTO.getNameSale())
                .discount(saleDTO.getDiscount())
                .idStatus(saleDTO.getIdStatus())
                .descriptionSale(saleDTO.getDescriptionSale())
                .build();
    }
}
