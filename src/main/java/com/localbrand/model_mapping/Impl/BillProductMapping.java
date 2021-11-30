package com.localbrand.model_mapping.Impl;

import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.request.ProductDetailBillRequestDTO;
import com.localbrand.dto.response.BillProductResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.BillProductRepository;
import com.localbrand.repository.ProductDetailRepository;
import com.localbrand.repository.ProductSaleRepository;
import com.localbrand.repository.SaleRepository;
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
    private final ProductSaleRepository productSaleRepository;
    private final SaleRepository saleRepository;

    @Override
    public BillProductResponseDTO toDto(BillProduct billProduct) {

        ProductDetail productDetail = this.productDetailRepository.findById(billProduct.getIdProductDetail().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.PRODUCT_DETAIL_IS_NULL));

        return BillProductResponseDTO
                .builder()
                .idBillProduct(billProduct.getIdBillProduct())
                .idBill(billProduct.getIdBill())
                .productChildResponseDTO(this.productMapping.toProductChild(productDetail))
                .quantity(billProduct.getQuantity())
                .price(billProduct.getPrice())
                .idStatus(billProduct.getIdStatus())
                .build();
    }

    @Override
    public BillProduct toEntity(BillProductResponseDTO billProductResponseDTO) {
        return null;
    }

    public List<BillProduct> toListProduct(Bill bill, BillRequestDTO billRequestDTO) {

        List<BillProduct> lstBillProducts = new ArrayList<>();

        for (ProductDetailBillRequestDTO detailBillRequestDTO : billRequestDTO.getListProductDetail()) {

            BillProduct billProduct = this.billProductRepository.findFirstByIdBillAndAndIdProductDetail(bill.getIdBill().intValue(), detailBillRequestDTO.getIdProductDetail().intValue());

            if (Objects.nonNull(billProduct)) {
                billProduct.setQuantity(detailBillRequestDTO.getQuantity());
                billProduct.setIdStatus(detailBillRequestDTO.getIdStatus());
                lstBillProducts.add(billProduct);
            } else {
                ProductDetail productDetail = this.productDetailRepository.findById(detailBillRequestDTO.getIdProductDetail()).orElse(null);

                if (Objects.nonNull(productDetail)) {
                    ProductSale productSale = this.productSaleRepository.findFirstByIdProductDetailAndIdStatus(productDetail.getIdProductDetail().intValue(), Status_Enum.EXISTS.getCode()).orElse(null);

                    float price = productDetail.getPrice();

                    if (Objects.nonNull(productSale)) {
                        Sale sale = this.saleRepository.findById(productSale.getIdSale().longValue()).orElse(null);
                        if (Objects.nonNull(sale)) {
                            price = price / 100 * (100 - sale.getDiscount());
                        }
                    }

                    BillProduct billProductNull = BillProduct
                            .builder()
                            .idProductDetail(detailBillRequestDTO.getIdProductDetail().intValue())
                            .idBill(bill.getIdBill().intValue())
                            .idProductDetail(productDetail.getIdProductDetail().intValue())
                            .quantity(detailBillRequestDTO.getQuantity())
                            .price(price)
                            .idStatus(detailBillRequestDTO.getIdStatus())
                            .build();
                    lstBillProducts.add(billProductNull);
                }
            }
        }

        return lstBillProducts;

    }

}
