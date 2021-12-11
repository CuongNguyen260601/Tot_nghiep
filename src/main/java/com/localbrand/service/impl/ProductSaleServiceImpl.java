package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.CancelSaleRequestDTO;
import com.localbrand.dto.request.ProductSaleCancelRequestDTO;
import com.localbrand.dto.request.ProductSaleDetail;
import com.localbrand.dto.request.ProductSaleRequestDTO;
import com.localbrand.dto.response.ProductChildResponseDTO;
import com.localbrand.dto.response.ProductDetailSaleResponseDTO;
import com.localbrand.dto.response.ProductSaleListResponseDTO;
import com.localbrand.dto.response.ProductSaleResponseDTO;
import com.localbrand.entity.ProductDetail;
import com.localbrand.entity.ProductSale;
import com.localbrand.entity.Sale;
import com.localbrand.model_mapping.Impl.ProductMapping;
import com.localbrand.model_mapping.Impl.ProductSaleMapping;
import com.localbrand.model_mapping.Impl.SaleMapping;
import com.localbrand.repository.ProductDetailRepository;
import com.localbrand.repository.ProductSaleRepository;
import com.localbrand.repository.SaleRepository;
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

    @Override
    public ServiceResult<ProductSaleResponseDTO> addSaleToProductDetail(ProductSaleRequestDTO productSaleRequestDTO) {

        Sale sale = this.saleMapping.toEntity(productSaleRequestDTO.getSale());

        if(Objects.isNull(sale.getIdSale())){
            sale = this.saleRepository.save(sale);
        }else{
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


        List<Integer> listProductDetailId = new ArrayList<>();

        List<Long> lstIdProductDetail = new ArrayList<>();

        for (ProductSaleCancelRequestDTO productSaleDetails: cancelSaleRequestDTO.getListProductDetail()) {
            listProductDetailId.add(productSaleDetails.getIdProductDetail());
            lstIdProductDetail.add(productSaleDetails.getIdProductDetail().longValue());
        }

        List<ProductSale> productSales = this.productSaleRepository.findAllByListProductSaleId(sale.getIdSale().intValue(), listProductDetailId);

        for (ProductSale productSale: productSales){
            for (ProductSaleCancelRequestDTO productSaleDetails: cancelSaleRequestDTO.getListProductDetail()) {
                if(productSaleDetails.getIdProductDetail().equals(productSale.getIdProductDetail())){
                    productSale.setIdStatus(productSaleDetails.getIdStatus());
                }
            }
        }
        this.productSaleRepository.saveAll(productSales);

        List<ProductDetail> productDetails = this.productDetailRepository.findAllByListIdProductDetail(lstIdProductDetail);

        List<ProductChildResponseDTO> productChildResponseDTOS = productDetails.stream().map(this.productMapping::toProductChild).collect(Collectors.toList());

        ProductSaleResponseDTO productSaleResponseDTO = ProductSaleResponseDTO.builder()
                .lstProductChild(productChildResponseDTOS)
                .saleDTO(this.saleMapping.toDto(sale))
                .build();

        return new ServiceResult<>(HttpStatus.OK, "Cancel sale is success", productSaleResponseDTO);
    }

    @Override
    public ServiceResult<ProductSaleResponseDTO> getListProductSale(Optional<Integer> idSale,Optional<Integer> page, Optional<Integer> limit) {
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(0));

        Sale sale = this.saleRepository.findById(idSale.get().longValue()).orElse(null);

        List<ProductSale> productSales = this.productSaleRepository.findAllByIdStatusAndIdSale(Status_Enum.EXISTS.getCode(), idSale.get(),pageable).toList();

        List<Long> listProductDetailId = productSales.stream().map(productSale -> productSale.getIdProductDetail().longValue()).collect(Collectors.toList());

        List<ProductDetail> productDetails = this.productDetailRepository.findAllByListIdProductDetail(listProductDetailId);

        List<ProductChildResponseDTO> productChildResponseDTOS = productDetails.stream().map(this.productMapping::toProductChild).collect(Collectors.toList());

        ProductSaleResponseDTO productSaleResponseDTO = ProductSaleResponseDTO.builder()
                .lstProductChild(productChildResponseDTOS)
                .build();

        if(Objects.nonNull(sale)){
            productSaleResponseDTO.setSaleDTO(this.saleMapping.toDto(sale));
        }
        return new ServiceResult<>(HttpStatus.OK, "get list product sale is success", productSaleResponseDTO);

    }
}
