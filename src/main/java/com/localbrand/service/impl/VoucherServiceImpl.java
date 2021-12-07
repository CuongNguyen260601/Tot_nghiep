package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.VoucherDTO;
import com.localbrand.entity.Voucher;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.VoucherMapping;
import com.localbrand.repository.VoucherRepository;
import com.localbrand.service.VoucherService;
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
public class VoucherServiceImpl implements VoucherService{
    private final Logger log = LoggerFactory.getLogger(SizeServiceImpl.class);

    private final VoucherRepository voucherRepository;
    private final VoucherMapping voucherMapping;

    @Override
    public ServiceResult<List<VoucherDTO>> findAll(Optional<Integer> page) {

        this.log.info("Get list voucher with page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Voucher> listVoucher = this.voucherRepository.findAll(pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.GET_LIST_VOUCHER_SUCCESS, listVoucher.stream().map(this.voucherMapping::toDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<List<VoucherDTO>> findAllAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page) {

        this.log.info("Get list voucher with page and sort");

        if(page.isEmpty()
                || page.get() < 0)
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if (sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC, "discount"));
        else if (sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC, "discount"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Voucher> listVoucher;

        if (idStatus.isEmpty()
            || idStatus.get() < 1)
            listVoucher = this.voucherRepository.findAll(pageable).toList();
        else
            listVoucher = this.voucherRepository.findAllByIdStatus(idStatus.get(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.GET_VOUCHER_AND_SORT_SUCESS, listVoucher.stream().map(this.voucherMapping::toDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<VoucherDTO> getById(Optional<Long> idVoucher) {
        this.log.info("Get voucher by id");

        if (idVoucher.isEmpty() || idVoucher.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Voucher.Validate_Voucher.VALIDATE_ID, null);

        Voucher voucher = this.voucherRepository.findById(idVoucher.orElse(1L)).orElse(null);

        if (Objects.isNull(voucher))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Voucher.GET_VOUCHER_BY_ID_FALSE, null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.GET_VOUCHER_BY_ID_SUCCESS,this.voucherMapping.toDto(voucher));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<VoucherDTO> save(VoucherDTO voucherDTO) {

        this.log.info("Save voucher: "+ voucherDTO);

        try {

            int randomCodeVoucher =(int) (Math.random()*100000+1);

            String codeVoucher = "VC"+randomCodeVoucher;

            Voucher voucher = this.voucherMapping.toEntity(voucherDTO);

            voucher.setCodeVoucher(codeVoucher);

            Voucher voucherSaved = this.voucherRepository.save(voucher);

            return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.SAVE_VOUCHER_SUCCESS, this.voucherMapping.toDto(voucherSaved));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Voucher.SAVE_VOUCHER_FALSE, null);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<VoucherDTO> delete(VoucherDTO voucherDTO) {
        this.log.info("Delete voucher: "+ voucherDTO);

        try {
            voucherDTO.setIdStatus(Status_Enum.DELETE.getCode());

            Voucher voucher = this.voucherMapping.toEntity(voucherDTO);

            Voucher voucherDelete = this.voucherRepository.save(voucher);

            return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.DELETE_VOUCHER_SUCCESS, this.voucherMapping.toDto(voucherDelete));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Voucher.DELETE_VOUCHER_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<VoucherDTO>> searchByName(String nameVoucher, Optional<Integer> page) {

        this.log.info("Get list voucher by name and page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Voucher> listVoucher = this.voucherRepository.findAllByNameVoucherLike("%"+nameVoucher+"%",pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.SEARCH_VOUCHER_SUCCESS, listVoucher.stream().map(this.voucherMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<VoucherDTO>> findByStatus(Optional<Integer> idStatus, Optional<Integer> page) {

        this.log.info("Get list voucher by status and page");

        if(idStatus.isEmpty()
                || idStatus.get() < 1
                || idStatus.get() > 10
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Size.Validate_Size.VALIDATE_STATUS);

        if(page.isEmpty()
                || page.get() < 0
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Voucher> listVoucher = this.voucherRepository.findAllByIdStatus(idStatus.orElse(2),pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.FIND_VOUCHER_BY_STATUS_SUCCESS, listVoucher.stream().map(this.voucherMapping::toDto).collect(Collectors.toList()));
    }
}
