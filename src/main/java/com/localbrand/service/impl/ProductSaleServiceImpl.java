package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.CancelSaleRequestDTO;
import com.localbrand.dto.request.ProductSaleCancelRequestDTO;
import com.localbrand.dto.request.ProductSaleDetail;
import com.localbrand.dto.request.ProductSaleRequestDTO;
import com.localbrand.dto.response.*;
import com.localbrand.entity.*;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.ProductMapping;
import com.localbrand.model_mapping.Impl.ProductSaleMapping;
import com.localbrand.model_mapping.Impl.SaleMapping;
import com.localbrand.repository.*;
import com.localbrand.service.ProductSaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSaleServiceImpl implements ProductSaleService {

    private final ProductSaleRepository productSaleRepository;
    private final ProductSaleMapping productSaleMapping;
    private final ProductDetailRepository productDetailRepository;
    private final SaleMapping saleMapping;
    private final SaleRepository saleRepository;
    private final ProductMapping productMapping;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public ServiceResult<ProductSaleResponseDTO> addSaleToProductDetail(ProductSaleRequestDTO productSaleRequestDTO) {

        Sale sale = this.saleMapping.toEntity(productSaleRequestDTO.getSale());

        if(Objects.isNull(sale.getIdSale())){
            sale = this.saleRepository.save(sale);
        }else{
            sale = this.saleRepository.save(sale);
            List<ProductSale> deleteListProductSale = this.productSaleRepository.findAllByIdSale(sale.getIdSale().intValue());
            this.productSaleRepository.deleteAll(deleteListProductSale);
        }

        List<ProductSale> productSales = new ArrayList<>();
        List<Integer> listProductDetailId = new ArrayList<>();

        for (ProductSaleDetail productSaleDetail: productSaleRequestDTO.getListProductDetail()) {
            productSales.add(
                    ProductSale.builder()
                            .idSale(sale.getIdSale().intValue())
                            .idProductDetail(productSaleDetail.getIdProductDetail())
                            .dateStart(productSaleRequestDTO.getDateStart())
                            .dateEnd(productSaleRequestDTO.getDateEnd())
                            .idStatus(productSaleDetail.getIdStatus())
                            .build()
            );
            listProductDetailId.add(productSaleDetail.getIdProductDetail());
        }
        this.productSaleRepository.UpdateSaleByIdProductDetail(Status_Enum.EXISTS.getCode(), Status_Enum.DELETE.getCode(),listProductDetailId);

        productSales = this.productSaleRepository.saveAll(productSales);

        return new ServiceResult<>(HttpStatus.OK, "Save sale to list product success", this.productSaleMapping.toDtoResponse(sale,productSales));
    }

    @Override
    public ServiceResult<ProductSaleResponseDTO> cancelSaleToProductDetail(CancelSaleRequestDTO cancelSaleRequestDTO) {

        Sale sale = this.saleRepository.findById(cancelSaleRequestDTO.getIdSale())
                .orElseThrow(() -> new RuntimeException("Invalid sale"));


        List<ProductSale> productSales = this.productSaleRepository.findAllByIdSale(sale.getIdSale().intValue());

        List<Long> lstIdProductDetail = new ArrayList<>();

        productSales.forEach(productSale -> {
            productSale.setIdStatus(cancelSaleRequestDTO.getIdStatus());
            lstIdProductDetail.add(productSale.getIdProductDetail().longValue());
        });

        this.productSaleRepository.saveAll(productSales);

        sale.setIdStatus(cancelSaleRequestDTO.getIdStatus());

        sale = this.saleRepository.save(sale);

        List<ProductDetail> productDetails = this.productDetailRepository.findAllByListIdProductDetail(lstIdProductDetail);

        List<ProductChildResponseDTO> productChildResponseDTOS = productDetails.stream().map(this.productMapping::toProductChild).collect(Collectors.toList());

        ProductSaleResponseDTO productSaleResponseDTO = ProductSaleResponseDTO.builder()
                .lstProductChild(productChildResponseDTOS)
                .saleDTO(this.saleMapping.toDto(sale))
                .build();

        if(productSales.size() > 0){
            productSaleResponseDTO.setDateStart(productSales.get(0).getDateStart());
            productSaleResponseDTO.setDateEnd(productSales.get(0).getDateEnd());
        }
        return new ServiceResult<>(HttpStatus.OK, "Cancel sale is success", productSaleResponseDTO);
    }

    @Override
    public ServiceResult<ProductSaleResponseDTO> getListProductSale(Optional<Integer> idSale,Optional<Integer> page, Optional<Integer> limit) {
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(0));

        Sale sale = this.saleRepository.findById(idSale.get().longValue()).orElse(null);

        List<ProductSale> productSales = this.productSaleRepository.findAllByIdSale(idSale.get(),pageable).toList();

        List<Long> listProductDetailId = productSales.stream().map(productSale -> productSale.getIdProductDetail().longValue()).collect(Collectors.toList());

        List<ProductDetail> productDetails = this.productDetailRepository.findAllByListIdProductDetail(listProductDetailId);

        List<ProductChildResponseDTO> productChildResponseDTOS = productDetails.stream().map(this.productMapping::toProductChild).collect(Collectors.toList());

        ProductSaleResponseDTO productSaleResponseDTO = ProductSaleResponseDTO.builder()
                .lstProductChild(productChildResponseDTOS)
                .build();


        if(productSales.size() > 0){
            productSaleResponseDTO.setDateStart(productSales.get(0).getDateStart());
            productSaleResponseDTO.setDateEnd(productSales.get(0).getDateEnd());
        }

        if(Objects.nonNull(sale)){
            productSaleResponseDTO.setSaleDTO(this.saleMapping.toDto(sale));
        }
        return new ServiceResult<>(HttpStatus.OK, "get list product sale is success", productSaleResponseDTO);
    }


    @Override
    public ServiceResult<List<ProductShowUserResponseDTO>> getListProductSaleUser(Optional<Integer> page, Optional<Integer> limit, Optional<Long> userId) {

        User user;

        if(userId.isEmpty() || userId.get() < 1){
            user = null;
        }else{
            user = this.userRepository.findById(userId.get()).orElse(null);
        }

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());
        List<Product> listProduct = this.productRepository.findAllByIsSale(Status_Enum.EXISTS.getCode(), Status_Enum.EXISTS.getCode(), Status_Enum.EXISTS.getCode(), pageable).toList();

        List<ProductShowUserResponseDTO> listProductShowUserResponseDTOS;

        if(Objects.isNull(user)){
            listProductShowUserResponseDTOS = listProduct.stream().map(this.productMapping::toProductShowUser).collect(Collectors.toList());
        }else {
            listProductShowUserResponseDTOS = listProduct.stream().map(product -> this.productMapping.toProductShowUserAndLike(product, user)).collect(Collectors.toList());
        }
        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_ON_USER_SUCCESS, listProductShowUserResponseDTOS);

    }
}
