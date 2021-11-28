package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.request.ComboDetailRequestDTO;
import com.localbrand.dto.request.ComboRequestDTO;
import com.localbrand.dto.response.ComboResponseDTO;
import com.localbrand.entity.Combo;
import com.localbrand.entity.ComboDetail;
import com.localbrand.entity.ComboTag;
import com.localbrand.entity.ProductDetail;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.ComboDetailMapping;
import com.localbrand.model_mapping.Impl.ComboMapping;
import com.localbrand.repository.ComboDetailRepository;
import com.localbrand.repository.ComboRepository;
import com.localbrand.repository.ComboTagRepository;
import com.localbrand.repository.ProductDetailRepository;
import com.localbrand.service.ComboService;
import com.localbrand.utils.Role_Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComboServiceImpl implements ComboService {

    private final Logger log = LoggerFactory.getLogger(ComboServiceImpl.class);

    private final ComboMapping comboMapping;
    private final ComboDetailMapping comboDetailMapping;
    private final ComboRepository comboRepository;
    private final ComboDetailRepository comboDetailRepository;
    private final ComboTagRepository comboTagRepository;
    private final ProductDetailRepository productDetailRepository;
    private final Role_Utils role_utils;

    public ComboServiceImpl(ComboMapping comboMapping, ComboDetailMapping comboDetailMapping, ComboRepository comboRepository, ComboDetailRepository comboDetailRepository, ComboTagRepository comboTagRepository, ProductDetailRepository productDetailRepository, Role_Utils role_utils) {
        this.comboMapping = comboMapping;
        this.comboDetailMapping = comboDetailMapping;
        this.comboRepository = comboRepository;
        this.comboDetailRepository = comboDetailRepository;
        this.comboTagRepository = comboTagRepository;
        this.productDetailRepository = productDetailRepository;
        this.role_utils = role_utils;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<ComboResponseDTO> addCombo(HttpServletRequest request, ComboRequestDTO comboRequestDTO) {

        this.log.info("Save combo: "+ comboRequestDTO);

        try {
            Object email = request.getAttribute("USER_NAME");

            if(Objects.nonNull(email)){
                Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COMBO.getModule(), Action_Enum.SAVE.getAction());
                if(!checkRole){
                    return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save combo", null);
                }
            }else{
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save combo", null);
            }

            Combo combo = this.comboMapping.toEntity(comboRequestDTO);

            List<ComboDetail> listComboDetails = new ArrayList<>();

            for (ComboDetailRequestDTO dto: comboRequestDTO.getComboDetailRequestDTO()) {

                ProductDetail productDetail = productDetailRepository.findById(dto.getIdProductDetail().longValue()).orElse(null);

                if(Objects.isNull(productDetail)){
                    return new ServiceResult<>(HttpStatus.BAD_REQUEST,Notification.Combo.SAVE_COMBO_FALSE, null);
                }else {
                    //check số lượng của sản phẩm còn lại đề tạo combo
                    if(comboRequestDTO.getQuantity() <= productDetail.getQuantity()){
                        listComboDetails.add(new ComboDetail(
                                dto.getIdComboDetail(),
                                null,
                                dto.getIdProductDetail()
                        ));
                    }else {
                        return new ServiceResult<>(HttpStatus.BAD_REQUEST,"Số lượng tối đa bạn có thể tạo là : "+ productDetail.getQuantity() , null);
                    }
                }
            }
            Combo comboSaved = this.comboRepository.save(combo);
            listComboDetails = listComboDetails.stream().peek(
                    detail -> detail.setIdCombo(comboSaved.getIdCombo().intValue())
            ).collect(Collectors.toList());

            this.comboDetailRepository.saveAll(listComboDetails);
            return new ServiceResult<>(HttpStatus.OK,Notification.Combo.SAVE_COMBO_SUCCESS, null);
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR,Notification.Combo.SAVE_COMBO_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<ComboResponseDTO>> findAllCombo(HttpServletRequest request, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page) {
        this.log.info("Get list combo for admin" + page);

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COMBO.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get combo", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get combo", null);
        }

        if (page.isEmpty() || page.get() < 0){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable;

        if(sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC,"nameCombo"));
        else if(sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC,"nameCombo"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Combo> listCombo = new ArrayList<>() ;

        if(idStatus.isEmpty() || idStatus.get() < 1){
            listCombo = this.comboRepository.findAll(pageable).toList();
        }else {
            listCombo = this.comboRepository.findAllByIdStatus(idStatus.get(), pageable).toList();
        }

        List<ComboResponseDTO> listComboResponseDTO = listCombo.stream().map(comboMapping::toDto).collect(Collectors.toList());

        return new ServiceResult<>(HttpStatus.OK, Notification.Combo.GET_LIST_COMBO_SUCCESS, listComboResponseDTO);
    }

    @Override
    public ServiceResult<List<ComboResponseDTO>> findAllComboUser(HttpServletRequest request, Optional<Integer> limit, Optional<Integer> page) {
        this.log.info("Get list combo for user" + page);

        if (page.isEmpty() || page.get() < 0){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<Combo> listCombo = this.comboRepository.findAll(pageable).toList();

        List<ComboResponseDTO> listComboResponseDTO = listCombo.stream().map(comboMapping::toDto).collect(Collectors.toList());

        return new ServiceResult<>(HttpStatus.OK, Notification.Combo.GET_LIST_COMBO_SUCCESS, listComboResponseDTO);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<ComboResponseDTO> delete(HttpServletRequest request,Optional<Long> idCombo) {

        this.log.info("delete combo by id : " + idCombo);

        try {

            Object email = request.getAttribute("USER_NAME");

            if(Objects.nonNull(email)){
                Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COMBO.getModule(), Action_Enum.READ.getAction());
                if(!checkRole){
                    return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not delete combo", null);
                }
            }else{
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not delete combo", null);
            }

            Combo combo =  this.comboRepository.findById(idCombo.orElse(1L)).orElse(null);
            if(Objects.isNull(combo)){
                return new ServiceResult<>(HttpStatus.BAD_REQUEST,Notification.Combo.DELETE_COMBO_FALSE,null);
            }

            if(combo.getIdStatus().equals(Status_Enum.DELETE.getCode()))
                combo.setIdStatus(Status_Enum.EXISTS.getCode());
            else
                combo.setIdStatus(Status_Enum.DELETE.getCode());

            combo = comboRepository.save(combo);

            return new ServiceResult<>(HttpStatus.OK,Notification.Combo.DELETE_COMBO_SUCCESS, this.comboMapping.toDto(combo));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR,Notification.Combo.DELETE_COMBO_FALSE, null);
        }
    }

    @Override
    public ServiceResult<ComboResponseDTO> getById(Optional<Long> idCombo) {

        this.log.info("Get combo by idCombo: " + idCombo);

        Combo combo =  this.comboRepository.findById(idCombo.orElse(1L)).orElse(null);
        if(Objects.isNull(combo)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,Notification.Combo.GET_COMBO_BY_ID_FALSE,null);
        }
        List<ComboTag> listComboTag = comboTagRepository.findAllByIdCombo(idCombo.get().intValue());
        List<ComboDetail> listComboDetail = comboDetailRepository.findAllByIdCombo(idCombo.get().intValue());
        ComboResponseDTO comboResponseDTO = comboMapping.toDto(combo,listComboTag,listComboDetail.stream().map(this.comboDetailMapping::toDto).collect(Collectors.toList()));

        return new ServiceResult<>(HttpStatus.OK,Notification.Combo.GET_COMBO_BY_ID_SUCCESS, comboResponseDTO);
    }

    @Override
    public ServiceResult<List<ComboResponseDTO>> searchByName(HttpServletRequest request,String nameCombo, Optional<Integer> page) {

        this.log.info("Search combo with name : " + nameCombo);

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COMBO.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get combo", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get combo", null);
        }

        if (page.isEmpty() || page.get() < 0){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Combo> listCombo = this.comboRepository.findAllByNameComboLike("%"+nameCombo+"%",pageable).toList();

        List<ComboResponseDTO> listComboResponseDTO = listCombo.stream().map(comboMapping::toDto).collect(Collectors.toList());

        return new ServiceResult<>(HttpStatus.OK, Notification.Combo.SEARCH_COMBO_SUCCESS, listComboResponseDTO);
    }

    @Override
    public ServiceResult<List<ComboResponseDTO>> searchByNameUser(HttpServletRequest request,String nameCombo, Optional<Integer> page) {

        if (page.isEmpty() || page.get() < 0){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Combo> listCombo = this.comboRepository.findAllByNameComboLikeAndAndIdStatus("%"+nameCombo+"%",Status_Enum.EXISTS.getCode(),pageable).toList();

        List<ComboResponseDTO> listComboResponseDTO = listCombo.stream().map(comboMapping::toDto).collect(Collectors.toList());

        return new ServiceResult<>(HttpStatus.OK, Notification.Combo.SEARCH_COMBO_SUCCESS, listComboResponseDTO);
    }


}
