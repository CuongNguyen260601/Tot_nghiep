package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.ProductDetailDTO;
import com.localbrand.dto.response.BillProductResponseDTO;
import com.localbrand.entity.BillProduct;
import com.localbrand.entity.ProductDetail;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillProductMapping implements Mapping<BillProductResponseDTO, BillProduct> {

    private final ProductDetailRepository productDetailRepository;
    private final ProductMapping productMapping;

    @Override
    public BillProductResponseDTO toDto(BillProduct billProduct) {

        ProductDetail productDetail = this.productDetailRepository.findById(billProduct.getIdProductDetail().longValue()).orElse(null);

        return BillProductResponseDTO
                .builder()
                .idBillProduct(billProduct.getIdBillProduct())
                .idBill(billProduct.getIdBill())
                .productDetailDTO(this.productMapping.toProductDetailUserDTOByProductDetail(productDetail))
                .quantity(billProduct.getQuantity())
                .price(billProduct.getPrice())
                .idStatus(billProduct.getIdStatus())
                .build();
    }

    @Override
    public BillProduct toEntity(BillProductResponseDTO billProductResponseDTO) {
        return null;
    }

}
