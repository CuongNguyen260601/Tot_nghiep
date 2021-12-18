package com.localbrand.model_mapping.Impl;

import com.localbrand.common.Status_Enum;
import com.localbrand.dto.SaleDTO;
import com.localbrand.dto.SaleResponseDTO;
import com.localbrand.entity.ProductSale;
import com.localbrand.entity.Sale;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.ProductSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaleMapping implements Mapping<SaleDTO, Sale>{

    private final ProductSaleRepository productSaleRepository;

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

    public SaleResponseDTO toResponseDto(Sale sale) {
        List<ProductSale> productSales = this.productSaleRepository.findAllByIdSaleExists(sale.getIdSale().intValue(), Status_Enum.EXISTS.getCode());
        return SaleResponseDTO
                .builder()
                .idSale(sale.getIdSale())
                .nameSale(sale.getNameSale())
                .discount(sale.getDiscount())
                .idStatus(sale.getIdStatus())
                .descriptionSale(sale.getDescriptionSale())
                .isSale(productSales.size()>0)
                .build();
    }
}
