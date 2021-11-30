package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.*;
import com.localbrand.dto.response.CartProductResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CartProductMapping implements Mapping<CartProductDTO, CartProduct> {

    private final CategoryChildMapping categoryChildMapping;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final ColorMapping colorMapping;
    private final SizeRepository sizeRepository;
    private final SizeMapping sizeMapping;
    private final SaleRepository saleRepository;
    private final SaleMapping saleMapping;
    private final TagRepository tagRepository;
    private final ProductMapping productMapping;

    @Override
    public CartProductDTO toDto(CartProduct cartProduct) {

        return CartProductDTO
                .builder()
                .idCart(cartProduct.getIdCart())
                .idCartProduct(cartProduct.getIdCartProduct())
                .quantity(cartProduct.getQuantity())
                .build();

    }

    @Override
    public CartProduct toEntity(CartProductDTO cartProductDTO) {
        return CartProduct
                .builder()
                .idCart(cartProductDTO.getIdCart())
                .idCartProduct(cartProductDTO.getIdCartProduct())
                .quantity(cartProductDTO.getQuantity())
                .idProductDetail(cartProductDTO.getProductDetailDTO().getIdProductDetail().intValue())
                .build();
    }

    public CartProductDTO toDtoCartProduct(CartProduct cartProduct, ProductDetail productDetail){
        CartProductDTO cartProductDTO = this.toDto(cartProduct);

        ProductDetailDTO productDetailDTO = ProductDetailDTO
                .builder()
                .idProductDetail(productDetail.getIdProductDetail())
                .idProduct(productDetail.getIdProduct())
                .idGender(productDetail.getIdGender())
                .price(productDetail.getPrice())
                .quantity(productDetail.getQuantity())
                .dateCreate(productDetail.getDateCreate())
                .detailPhoto(productDetail.getDetailPhoto())
                .build();

        Category category = this.categoryRepository.findById(productDetail.getIdCategory().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.CATEGORY_IS_NULL));

        Color color = this.colorRepository.findById(productDetail.getIdColor().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.COLOR_IS_NULL));

        Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.SIZE_IS_NULL));

        Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

        List<Integer> listTag = this.tagRepository.findByIdProductDetail(productDetail.getIdProductDetail());

        productDetailDTO.setCategoryDTO(this.categoryChildMapping.toDto(category));

        productDetailDTO.setColorDTO(this.colorMapping.toDto(color));

        productDetailDTO.setSizeDTO(this.sizeMapping.toDto(size));

        if(Objects.nonNull(sale)){
            SaleDTO saleDTO = this.saleMapping.toDto(sale);

            productDetailDTO.setSaleDTO(saleDTO);

        }

        productDetailDTO.setListTagDTO(listTag);

        cartProductDTO.setProductDetailDTO(productDetailDTO);

        return cartProductDTO;
    }

    public CartProductResponseDTO toCartUserDTO(CartProduct cartProduct, ProductDetail productDetail){

        return CartProductResponseDTO
                .builder()
                .idCart(cartProduct.getIdCart())
                .idCartProduct(cartProduct.getIdCartProduct())
                .productDetailDTO(this.productMapping.toProductDetailUserDTOByProductDetail(productDetail))
                .quantity(cartProduct.getQuantity())
                .build();
    }
}
