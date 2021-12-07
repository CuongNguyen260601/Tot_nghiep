package com.localbrand.service.impl;

import com.localbrand.common.Config_Enum;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ComboDetailRequestDTO;
import com.localbrand.dto.response.ComboDetailResponseDTO;
import com.localbrand.entity.ComboDetail;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.ComboDetailMapping;
import com.localbrand.repository.ComboDetailRepository;
import com.localbrand.service.ComboDetailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComboDetailServiceImpl implements ComboDetailService {
    private final Logger log = LoggerFactory.getLogger(ComboDetailServiceImpl.class);

    private final ComboDetailRepository comboDetailRepository;
    private final ComboDetailMapping comboDetailMapping;


    @Override
    public ServiceResult<ComboDetailResponseDTO> saveComboDetail(ComboDetailRequestDTO comboDetailRequestDTO) {
        return null;
    }

    @Override
    public ServiceResult<List<ComboDetailResponseDTO>> findAll(Optional<Integer> page) {

        this.log.info("Get list combo detail with page" + page);

        if (page.isEmpty() || page.get() < 0){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<ComboDetail> listComboDetail = this.comboDetailRepository.findAll(pageable).toList();

        List<ComboDetailResponseDTO> listComboDetailResponseDTO = listComboDetail.stream().map(comboDetailMapping::toDto).collect(Collectors.toList());

        return new ServiceResult<>(HttpStatus.OK, Notification.ComboDetail.GET_LIST_COMBO_DETAIL_SUCCESS, listComboDetailResponseDTO);
    }

    @Override
    public ServiceResult<ComboDetailResponseDTO> getById(Optional<Long> idComboDetail) {

        this.log.info("Get combo detail by id : " + idComboDetail);

        ComboDetail comboDetail =  this.comboDetailRepository.findById(idComboDetail.orElse(1L)).orElse(null);
        if(Objects.isNull(comboDetail)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,Notification.Combo.GET_COMBO_BY_ID_FALSE,null);
        }
        ComboDetailResponseDTO comboDetailResponseDTO = comboDetailMapping.toDto(comboDetail);

        return new ServiceResult<>(HttpStatus.OK,Notification.Combo.GET_COMBO_BY_ID_SUCCESS, comboDetailResponseDTO);
    }

    @Override
    public ServiceResult<ComboDetailResponseDTO> delete(Optional<Long> idComboDetail) {
        return null;
    }

    @Override
    public ServiceResult<List<ComboDetailResponseDTO>> findByIdCombo(Optional<Integer> idCombo, Optional<Integer> page) {

        this.log.info("Get list combo detail by id combo");

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
