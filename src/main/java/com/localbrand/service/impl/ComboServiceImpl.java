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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {

    private final Logger log = LoggerFactory.getLogger(ComboServiceImpl.class);

    private final ComboMapping comboMapping;
    private final ComboDetailMapping comboDetailMapping;
    private final ComboRepository comboRepository;
    private final ComboDetailRepository comboDetailRepository;
    private final ComboTagRepository comboTagRepository;
    private final ProductDetailRepository productDetailRepository;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<ComboResponseDTO> addCombo(ComboRequestDTO comboRequestDTO) {

        this.log.info("Save combo: "+ comboRequestDTO);

        try {

            Combo combo = this.comboMapping.toEntity(comboRequestDTO);

            List<ComboDetail> listComboDetails = new ArrayList<>();

            List<ProductDetail> productDetailsInCombo = new ArrayList<>();

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
                        return new ServiceResult<>(HttpStatus.OK,"Số lượng tối đa bạn có thể tạo là : "+ productDetail.getQuantity() , null);
                    }
                }
            }

            List<Long> listIdProductDetail = new ArrayList<>();

            for (ComboDetailRequestDTO comboDetailRequestDTO : comboRequestDTO.getComboDetailRequestDTO()) {
                Long longValue = comboDetailRequestDTO.getIdProductDetail().longValue();
                listIdProductDetail.add(longValue);
            }

            if(Objects.nonNull(comboRequestDTO.getIdCombo())){
                productDetailsInCombo = productDetailsInCombo.stream().map(productDetail -> {
                    productDetail.setQuantity(productDetail.getQuantity()-comboRequestDTO.getQuantity());
                    return productDetail;
                }).collect(Collectors.toList());
                this.productDetailRepository.saveAll(productDetailsInCombo);
            }

            Combo comboSaved = this.comboRepository.save(combo);

            listComboDetails = listComboDetails.stream().peek(
                    detail -> detail.setIdCombo(comboSaved.getIdCombo().intValue())
            ).collect(Collectors.toList());

            this.comboDetailRepository.saveAll(listComboDetails);
            return new ServiceResult<>(HttpStatus.OK,"Lưu combo thành công!", null);
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR,Notification.Combo.SAVE_COMBO_FALSE, null);
        }
    }


    @Override
    public ServiceResult<List<ComboResponseDTO>> findAllCombo(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page) {
        this.log.info("Get list combo for admin" + page);

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
    public ServiceResult<List<ComboResponseDTO>> findAllComboUser(Optional<Integer> limit, Optional<Integer> page) {
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
    public ServiceResult<ComboResponseDTO> delete(Optional<Long> idCombo) {

        this.log.info("delete combo by id : " + idCombo);

        try {

            Combo combo =  this.comboRepository.findById(idCombo.orElse(0L)).orElse(null);
            if(Objects.isNull(combo)){
                return new ServiceResult<>(HttpStatus.BAD_REQUEST,Notification.Combo.DELETE_COMBO_FALSE,null);
            }

            if(combo.getIdStatus().equals(Status_Enum.DELETE.getCode()))
                combo.setIdStatus(Status_Enum.EXISTS.getCode());
            else
                combo.setIdStatus(Status_Enum.DELETE.getCode());


            List<ComboDetail> comboDetails = this.comboDetailRepository.findAllByIdCombo(combo.getIdCombo().intValue());

            List<Long> productDetailIds = new ArrayList<>();

            comboDetails.forEach(comboDetail -> {
                productDetailIds.add(comboDetail.getIdProductDetail().longValue());
            });

            List<ProductDetail> productDetailsInCombo = this.productDetailRepository.findAllByListIdProductDetailAndSort(productDetailIds);

            for (ProductDetail productDetail : productDetailsInCombo){
                productDetail.setQuantity(productDetail.getQuantity()+combo.getQuantity());
            }

            this.productDetailRepository.saveAll(productDetailsInCombo);

            combo.setQuantity(0);

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
    public ServiceResult<List<ComboResponseDTO>> searchByName(String nameCombo, Optional<Integer> page) {

        this.log.info("Search combo with name : " + nameCombo);

        if (page.isEmpty() || page.get() < 0){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Combo> listCombo = this.comboRepository.findAllByNameComboLike("%"+nameCombo+"%",pageable).toList();

        List<ComboResponseDTO> listComboResponseDTO = listCombo.stream().map(comboMapping::toDto).collect(Collectors.toList());

        return new ServiceResult<>(HttpStatus.OK, Notification.Combo.SEARCH_COMBO_SUCCESS, listComboResponseDTO);
    }

    @Override
    public ServiceResult<List<ComboResponseDTO>> searchByNameUser(String nameCombo, Optional<Integer> page) {

        if (page.isEmpty() || page.get() < 0){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), 9);

        List<Combo> listCombo = this.comboRepository.findAllByNameComboLikeAndAndIdStatus("%"+nameCombo+"%",Status_Enum.EXISTS.getCode(),pageable).toList();

        List<ComboResponseDTO> listComboResponseDTO = listCombo.stream().map(comboMapping::toDto).collect(Collectors.toList());

        return new ServiceResult<>(HttpStatus.OK, Notification.Combo.SEARCH_COMBO_SUCCESS, listComboResponseDTO);
    }

}