package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.VoucherDTO;
import com.localbrand.entity.Voucher;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.VoucherMapping;
import com.localbrand.repository.VoucherRepository;
import com.localbrand.service.VoucherService;
import com.localbrand.utils.Role_Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    private final Role_Utils role_utils;

    @Override
    public ServiceResult<List<VoucherDTO>> findAll(HttpServletRequest request, Optional<Integer> page) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.VOUCHER.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
        }

        this.log.info("Get list voucher with page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Voucher> listVoucher = this.voucherRepository.findAll(pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.GET_LIST_VOUCHER_SUCCESS, listVoucher.stream().map(this.voucherMapping::toDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<List<VoucherDTO>> findAllAndSort(HttpServletRequest request, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.VOUCHER.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
        }

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
    public ServiceResult<VoucherDTO> getById(HttpServletRequest request, Optional<Long> idVoucher) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.VOUCHER.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
        }

        this.log.info("Get voucher by id");

        if (idVoucher.isEmpty() || idVoucher.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Voucher.Validate_Voucher.VALIDATE_ID, null);

        Voucher voucher = this.voucherRepository.findById(idVoucher.orElse(1L)).orElse(null);

        if (Objects.isNull(voucher))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Voucher.GET_VOUCHER_BY_ID_FALSE, null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.GET_VOUCHER_BY_ID_SUCCESS,this.voucherMapping.toDto(voucher));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<VoucherDTO> save(HttpServletRequest request, VoucherDTO voucherDTO) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.VOUCHER.getModule(), Action_Enum.SAVE.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save voucher", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save voucher", null);
        }

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
    public ServiceResult<VoucherDTO> delete(HttpServletRequest request, VoucherDTO voucherDTO) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.VOUCHER.getModule(), Action_Enum.DELETE.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not delete voucher", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not delete voucher", null);
        }

        this.log.info("Delete voucher: "+ voucherDTO);

        try {
            voucherDTO.setIdStatus(Status_Enum.DELETE.getCode());

            if(voucherDTO.getDateEnd().before(voucherDTO.getDateStart()))
                return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Voucher.Validate_Voucher.VALIDATE_DATE_END, null);

            Voucher voucher = this.voucherMapping.toEntity(voucherDTO);

            Voucher voucherDelete = this.voucherRepository.save(voucher);

            return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.DELETE_VOUCHER_SUCCESS, this.voucherMapping.toDto(voucherDelete));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Voucher.DELETE_VOUCHER_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<VoucherDTO>> searchByName(HttpServletRequest request, String nameVoucher, Optional<Integer> page) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.VOUCHER.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
        }

        this.log.info("Get list voucher by name and page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Voucher> listVoucher = this.voucherRepository.findAllByNameVoucherLike("%"+nameVoucher+"%",pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Voucher.SEARCH_VOUCHER_SUCCESS, listVoucher.stream().map(this.voucherMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<VoucherDTO>> findByStatus(HttpServletRequest request, Optional<Integer> idStatus, Optional<Integer> page) {
        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.VOUCHER.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get voucher", null);
        }

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
