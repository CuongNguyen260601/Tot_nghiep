package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.ProductSaleCancelRequestDTO;
import com.localbrand.dto.request.ProductSaleDetail;
import com.localbrand.dto.request.ProductSaleRequestDTO;
import com.localbrand.dto.response.ProductSaleListResponseDTO;
import com.localbrand.dto.response.ProductSaleResponseDTO;
import com.localbrand.entity.ProductSale;
import com.localbrand.entity.Sale;
import com.localbrand.model_mapping.Impl.ProductSaleMapping;
import com.localbrand.model_mapping.Impl.SaleMapping;
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
    private final SaleMapping saleMapping;
    private final SaleRepository saleRepository;

    @Override
    public ServiceResult<ProductSaleResponseDTO> addSaleToProductDetail(ProductSaleRequestDTO productSaleRequestDTO) {

        Sale sale = this.saleMapping.toEntity(productSaleRequestDTO.getSale());

        if(Objects.isNull(sale.getIdSale())){
            sale = this.saleRepository.save(sale);
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
    public ServiceResult<List<ProductSaleListResponseDTO>> cancelSaleToProductDetail(List<ProductSaleCancelRequestDTO> productSaleCancelRequestDTOS) {
        List<Integer> listProductDetailId = new ArrayList<>();

        for (ProductSaleCancelRequestDTO productSaleDetails: productSaleCancelRequestDTOS) {
            listProductDetailId.add(productSaleDetails.getIdProductSale().intValue());
        }
        List<ProductSale> productSales = this.productSaleRepository.findAllByListProductSaleId(listProductDetailId);

        for (ProductSale productSale: productSales){
            for (ProductSaleCancelRequestDTO productSaleDetails: productSaleCancelRequestDTOS) {
                if(productSaleDetails.getIdProductSale().equals(productSale.getIdProductSale())){
                    productSale.setIdStatus(productSaleDetails.getIdStatus());
                }
            }
        }

        productSales = this.productSaleRepository.saveAll(productSales);

        return new ServiceResult<>(HttpStatus.OK, "Cancel sale is success", productSales.stream().map(this.productSaleMapping::toDtoResponseList).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<ProductSaleListResponseDTO>> getListProductSale(Optional<Integer> idSale,Optional<Integer> page, Optional<Integer> limit) {
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(0));

        List<ProductSale> productSales = this.productSaleRepository.findAllByIdStatusAndIdSale(Status_Enum.EXISTS.getCode(), idSale.get(),pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, "Cancel sale is success", productSales.stream().map(this.productSaleMapping::toDtoResponseList).collect(Collectors.toList()));
    }
}
