package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.ProductDetailDTO;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.request.ProductDetailBillRequestDTO;
import com.localbrand.dto.response.BillProductResponseDTO;
import com.localbrand.entity.Bill;
import com.localbrand.entity.BillProduct;
import com.localbrand.entity.ProductDetail;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.BillProductRepository;
import com.localbrand.repository.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BillProductMapping implements Mapping<BillProductResponseDTO, BillProduct> {

    private final ProductDetailRepository productDetailRepository;
    private final ProductMapping productMapping;
    private final BillProductRepository billProductRepository;

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

    public List<BillProduct> toListProduct(Bill bill, BillRequestDTO billRequestDTO){

        List<BillProduct> lstBillProducts = new ArrayList<>();

        for (ProductDetailBillRequestDTO detailBillRequestDTO: billRequestDTO.getListProductDetail()) {

            BillProduct billProduct = this.billProductRepository.findFirstByIdBillAndAndIdProductDetail(bill.getIdBill().intValue(), detailBillRequestDTO.getIdProductDetail().intValue());

            if(Objects.nonNull(billProduct)){
                billProduct.setQuantity(detailBillRequestDTO.getQuantity());
                billProduct.setIdStatus(detailBillRequestDTO.getIdStatus());
                lstBillProducts.add(billProduct);
            }else{
                ProductDetail productDetail = this.productDetailRepository.findById(detailBillRequestDTO.getIdProductDetail()).orElse(null);

                if(Objects.nonNull(productDetail)){

                    BillProduct billProductNull = BillProduct
                            .builder()
                            .idProductDetail(detailBillRequestDTO.getIdProductDetail().intValue())
                            .idBill(bill.getIdBill().intValue())
                            .idProductDetail(productDetail.getIdProductDetail().intValue())
                            .quantity(detailBillRequestDTO.getQuantity())
                            .price(productDetail.getPrice())
                            .idStatus(detailBillRequestDTO.getIdStatus())
                            .build();
                    lstBillProducts.add(billProductNull);
                }
            }
        }

        return lstBillProducts;

    }

}
