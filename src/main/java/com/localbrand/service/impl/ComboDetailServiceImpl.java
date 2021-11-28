package com.localbrand.service.impl;

import com.localbrand.common.Action_Enum;
import com.localbrand.common.Config_Enum;
import com.localbrand.common.Module_Enum;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ComboDetailRequestDTO;
import com.localbrand.dto.response.ComboDetailResponseDTO;
import com.localbrand.entity.ComboDetail;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.ComboDetailMapping;
import com.localbrand.repository.ComboDetailRepository;
import com.localbrand.service.ComboDetailService;
import com.localbrand.utils.Role_Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComboDetailServiceImpl implements ComboDetailService {
    private final Logger log = LoggerFactory.getLogger(ComboDetailServiceImpl.class);

    private final ComboDetailRepository comboDetailRepository;
    private final ComboDetailMapping comboDetailMapping;
    private final Role_Utils role_utils;


    public ComboDetailServiceImpl(@Lazy ComboDetailRepository comboDetailRepository, ComboDetailMapping comboDetailMapping, Role_Utils role_utils) {
        this.comboDetailRepository = comboDetailRepository;
        this.comboDetailMapping = comboDetailMapping;
        this.role_utils = role_utils;
    }

    @Override
    public ServiceResult<ComboDetailResponseDTO> saveComboDetail(HttpServletRequest request, ComboDetailRequestDTO comboDetailRequestDTO) {
        return null;
    }

    @Override
    public ServiceResult<List<ComboDetailResponseDTO>> findAll(HttpServletRequest request,Optional<Integer> page) {

        this.log.info("Get list combo detail with page" + page);

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COMBO.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not find combodetail", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not find combodetail", null);
        }

        if (page.isEmpty() || page.get() < 0){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<ComboDetail> listComboDetail = this.comboDetailRepository.findAll(pageable).toList();

        List<ComboDetailResponseDTO> listComboDetailResponseDTO = listComboDetail.stream().map(comboDetailMapping::toDto).collect(Collectors.toList());

        return new ServiceResult<>(HttpStatus.OK, Notification.ComboDetail.GET_LIST_COMBO_DETAIL_SUCCESS, listComboDetailResponseDTO);
    }

    @Override
    public ServiceResult<ComboDetailResponseDTO> getById(HttpServletRequest request,Optional<Long> idComboDetail) {

        this.log.info("Get combo detail by id : " + idComboDetail);

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COMBO.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not find combodetail", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not find combodetail", null);
        }

        ComboDetail comboDetail =  this.comboDetailRepository.findById(idComboDetail.orElse(1L)).orElse(null);
        if(Objects.isNull(comboDetail)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,Notification.Combo.GET_COMBO_BY_ID_FALSE,null);
        }
        ComboDetailResponseDTO comboDetailResponseDTO = comboDetailMapping.toDto(comboDetail);

        return new ServiceResult<>(HttpStatus.OK,Notification.Combo.GET_COMBO_BY_ID_SUCCESS, comboDetailResponseDTO);
    }

    @Override
    public ServiceResult<ComboDetailResponseDTO> delete(HttpServletRequest request,Optional<Long> idComboDetail) {
        return null;
    }

    @Override
    public ServiceResult<List<ComboDetailResponseDTO>> findByIdCombo(HttpServletRequest request,Optional<Integer> idCombo, Optional<Integer> page) {

        this.log.info("Get list combo detail by id combo");

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COMBO.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not find combodetail", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not find combodetail", null);
        }

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0),100);

        Page<ComboDetail> listComboDetail = null;

        if(Objects.nonNull(idCombo)){
            listComboDetail = comboDetailRepository.findAllByIdCombo(idCombo.get(),pageable);
        }else {
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "Combo không tồn tại", null);
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.ComboDetail.GET_LIST_COMBO_DETAIL_BY_ID_COMBO_SUCCESS, listComboDetail.stream().map(this.comboDetailMapping::toDto).collect(Collectors.toList()));

    }
}
