package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.SaleDTO;
import com.localbrand.dto.SaleResponseDTO;
import com.localbrand.entity.Sale;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.SaleMapping;
import com.localbrand.repository.SaleRepository;
import com.localbrand.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService{

    private final Logger log = LoggerFactory.getLogger(SizeServiceImpl.class);

    private final SaleRepository saleRepository;
    private final SaleMapping saleMapping;

    @Override
    public ServiceResult<List<SaleResponseDTO>> findAll(Optional<Integer> page) {

        this.log.info("Get list sale with page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Sale> listSale = this.saleRepository.findAll(pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Sale.GET_LIST_SALE_SUCCESS, listSale.stream().map(this.saleMapping::toResponseDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<List<SaleResponseDTO>> findAllAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page) {

        this.log.info("Get list sale with page and sort");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if (sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC, "discount"));
        else if (sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC, "discount"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Sale> listSale;

        if (idStatus.isEmpty()
            || idStatus.get() < 1)
            listSale = this.saleRepository.findAll(pageable).toList();
        else
            listSale = this.saleRepository.findAllByIdStatus(idStatus.get(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Sale.GET_SALE_AND_SORT_SUCESS, listSale.stream().map(this.saleMapping::toResponseDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<SaleResponseDTO> getById(Optional<Long> idSale) {

        this.log.info("Get sale by id");

        if (idSale.isEmpty() || idSale.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Sale.Validate_Sale.VALIDATE_ID, null);

        Sale sale = this.saleRepository.findById(idSale.orElse(1L)).orElse(null);

        if (Objects.isNull(sale))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Sale.GET_SALE_BY_ID_FALSE, null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Sale.GET_SALE_BY_ID_SUCCESS,this.saleMapping.toResponseDto(sale));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<SaleResponseDTO> save(SaleDTO saleDTO) {
        this.log.info("Save sale: "+ saleDTO);

        try {
            Sale sale = this.saleMapping.toEntity(saleDTO);

            Sale saleSaved = this.saleRepository.save(sale);

            return new ServiceResult<>(HttpStatus.OK, Notification.Sale.SAVE_SALE_SUCCESS, this.saleMapping.toResponseDto(saleSaved));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Sale.SAVE_SALE_FALSE, null);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<SaleResponseDTO> delete(SaleDTO saleDTO) {

        this.log.info("Delete sale: "+ saleDTO);

        try {
            saleDTO.setIdStatus(Status_Enum.DELETE.getCode());

            Sale sale = this.saleMapping.toEntity(saleDTO);

            Sale saleDelete = this.saleRepository.save(sale);

            return new ServiceResult<>(HttpStatus.OK, Notification.Sale.DELETE_SALE_SUCCESS, this.saleMapping.toResponseDto(saleDelete));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Sale.DELETE_SALE_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<SaleResponseDTO>> searchByName(String nameSale, Optional<Integer> page) {

        this.log.info("Get list sale by name and page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Sale> listSale = this.saleRepository.findAllByNameSaleLike("%"+nameSale+"%",pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Sale.SEARCH_SALE_SUCCESS, listSale.stream().map(this.saleMapping::toResponseDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<SaleResponseDTO>> findByStatus(Optional<Integer> idStatus, Optional<Integer> page) {

        this.log.info("Get list sale by status and page");

        if(idStatus.isEmpty()
                || idStatus.get() < 1
                || idStatus.get() > 10
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Sale.Validate_Sale.VALIDATE_STATUS);

        if(page.isEmpty()
                || page.get() < 0
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Sale> listSale = this.saleRepository.findAllByIdStatus(idStatus.orElse(2),pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Sale.FIND_SALE_BY_STATUS_SUCCESS, listSale.stream().map(this.saleMapping::toResponseDto).collect(Collectors.toList()));
    }
}
