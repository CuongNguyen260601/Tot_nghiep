package com.localbrand.model_mapping.Impl;


import com.localbrand.dto.GenderDTO;
import com.localbrand.dto.response.ProductChildResponseDTO;
import com.localbrand.dto.response.ProductDetailSaleResponseDTO;
import com.localbrand.dto.response.ProductSaleListResponseDTO;
import com.localbrand.dto.response.ProductSaleResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductSaleMapping implements Mapping<ProductSaleResponseDTO, ProductSale> {

    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final CategoryParentMapping categoryParentMapping;
    private final CategoryChildMapping categoryChildMapping;
    private final ColorMapping colorMapping;
    private final SizeMapping sizeMapping;
    private final GenderRepository genderRepository;
    private final SaleMapping saleMapping;
    private final SaleRepository saleRepository;
    private final ProductMapping productMapping;

    @Override
    public ProductSaleResponseDTO toDto(ProductSale productSale) {
        return null;
    }

    @Override
    public ProductSale toEntity(ProductSaleResponseDTO productSaleResponseDTO) {
        return null;
    }

    public ProductSaleResponseDTO toDtoResponse(Sale sale, List<ProductSale> productSales) {

        List<Long> listIdProductDetail = new ArrayList<>();

        for (ProductSale productSale: productSales) {
            listIdProductDetail.add(productSale.getIdProductDetail().longValue());
        }

        List<ProductDetail> productDetails = this.productDetailRepository.findAllByListIdProductDetail(listIdProductDetail);

        List<ProductChildResponseDTO> productChildResponseDTOS = productDetails.stream().map(this.productMapping::toProductChild).collect(Collectors.toList());

        ProductSaleResponseDTO productSaleResponseDTO = ProductSaleResponseDTO.builder()
                .saleDTO(this.saleMapping.toDto(sale))
                .dateStart(productSales.get(0).getDateStart())
                .dateEnd(productSales.get(0).getDateEnd())
                .lstProductChild(productChildResponseDTOS)
                .build();

        if(productSales.size() > 0){
            productSaleResponseDTO.setDateStart(productSales.get(0).getDateStart());
            productSaleResponseDTO.setDateEnd(productSales.get(0).getDateEnd());
        }

        return productSaleResponseDTO;
    }

    public ProductDetailSaleResponseDTO toProductDetailSaleDTOByProductDetail(ProductDetail productDetail){

        Product product = this.productRepository.findById(productDetail.getIdProduct().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.PRODUCT_IS_NULL));

        Category category = this.categoryRepository.findById(productDetail.getIdCategory().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.CATEGORY_IS_NULL));

        Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.SIZE_IS_NULL));

        Color color = this.colorRepository.findById(productDetail.getIdColor().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.COLOR_IS_NULL));

        Category categoryParent = this.categoryRepository.findById(category.getParentId().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.CATEGORY_IS_NULL));

        Gender gender = this.genderRepository.findById(productDetail.getIdGender().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.GENDER_IS_NULL));

        ProductDetailSaleResponseDTO productDetailSaleResponseDTO = new ProductDetailSaleResponseDTO();

        productDetailSaleResponseDTO.setIdProduct(product.getIdProduct());
        productDetailSaleResponseDTO.setNameProduct(product.getNameProduct());
        productDetailSaleResponseDTO.setDateCreate(product.getDateCreate());
        productDetailSaleResponseDTO.setTotalProduct(product.getTotalProduct());
        productDetailSaleResponseDTO.setIdStatus(product.getIdStatus());
        productDetailSaleResponseDTO.setDescriptionProduct(product.getDescriptionProduct());
        productDetailSaleResponseDTO.setCoverPhoto(product.getCoverPhoto());
        productDetailSaleResponseDTO.setFrontPhoto(product.getFrontPhoto());
        productDetailSaleResponseDTO.setBackPhoto(product.getBackPhoto());
        productDetailSaleResponseDTO.setCategoryParentDTO(this.categoryParentMapping.toDto(categoryParent));
        productDetailSaleResponseDTO.setCategoryChildDTO(this.categoryChildMapping.toDto(category));
        productDetailSaleResponseDTO.setGenderDTO(GenderDTO.builder()
                        .idGender(gender.getIdGender())
                        .nameGender(gender.getNameGender())
                .build());
        productDetailSaleResponseDTO.setIdProductDetail(productDetail.getIdProductDetail());
        productDetailSaleResponseDTO.setIdGender(productDetail.getIdGender());
        productDetailSaleResponseDTO.setColorDTO(this.colorMapping.toDto(color));
        productDetailSaleResponseDTO.setPrice(productDetail.getPrice());
        productDetailSaleResponseDTO.setAmount(productDetail.getQuantity());
        productDetailSaleResponseDTO.setDateCreate(productDetail.getDateCreate());
        productDetailSaleResponseDTO.setDetailPhoto(productDetail.getDetailPhoto());
        productDetailSaleResponseDTO.setCategoryDTO(this.categoryChildMapping.toDto(category));
        productDetailSaleResponseDTO.setSizeDTO(this.sizeMapping.toDto(size));

        return productDetailSaleResponseDTO;

    }


    public ProductSaleListResponseDTO toDtoResponseList(ProductSale productSales) {

        Sale sale = this.saleRepository.findById(productSales.getIdSale().longValue()).
                orElseThrow(() -> new RuntimeException(ErrorCodes.SALE_IS_NULL));

        ProductDetail productDetail = this.productDetailRepository.findById(productSales.getIdProductDetail().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.PRODUCT_IS_NULL));

        ProductDetailSaleResponseDTO productDetailSaleResponseDTO = this.toProductDetailSaleDTOByProductDetail(productDetail);

        return ProductSaleListResponseDTO
                .builder()
                .sale(this.saleMapping.toDto(sale))
                .productDetail(productDetailSaleResponseDTO)
                .build();
    }


}
