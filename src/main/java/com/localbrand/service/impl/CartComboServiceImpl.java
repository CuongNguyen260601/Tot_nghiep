package com.localbrand.service.impl;

import com.localbrand.common.Config_Enum;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartComboDTO;
import com.localbrand.entity.CartCombo;
import com.localbrand.model_mapping.Impl.CartComboMapping;
import com.localbrand.repository.CartComboRepository;
import com.localbrand.service.CartComboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class CartComboServiceImpl implements CartComboService {
    private final Logger log = LoggerFactory.getLogger(CartComboServiceImpl.class);

    private final CartComboRepository cartComboRepository;
    private final CartComboMapping cartComboMapping;

    public CartComboServiceImpl(CartComboRepository cartComboRepository, CartComboMapping cartComboMapping) {
        this.cartComboRepository = cartComboRepository;
        this.cartComboMapping = cartComboMapping;
    }

    @Override
    public ServiceResult findByName(CartComboDTO cartComboDTO, Integer page) {
        this.log.debug("request to find all cart in page: {} - user: {}", page);
        Pageable pageable = PageRequest.of(page, Config_Enum.SIZE_PAGE.getCode());
        Page<Map<String, Object>> listComboByName = this.cartComboRepository.findListComboByName(
                cartComboDTO.getIdUser(), cartComboDTO.getCombo().getNameCombo(), pageable
        );
        return new ServiceResult(HttpStatus.OK, "", listComboByName);
    }

    @Transactional
    @Override
    public ServiceResult save(CartComboDTO cartComboDTO) {
        this.log.debug("request to add cart combo: {}", cartComboDTO);
        try {
            CartCombo cartCombo = this.cartComboMapping.toEntity(cartComboDTO);
            this.cartComboRepository.save(cartCombo);
            this.log.debug("add cart combo successfully");
            return new ServiceResult(HttpStatus.OK, "", cartCombo);
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            return new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Transactional
    @Override
    public ServiceResult delete(CartComboDTO cartComboDTO) {
        this.log.debug("request to delete cart combo: {}", cartComboDTO);
        try {
            CartCombo cartCombo = this.cartComboMapping.toEntity(cartComboDTO);
            this.cartComboRepository.delete(cartCombo);
            this.log.debug("delete cart combo successfully");
            return new ServiceResult(HttpStatus.OK, "");
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            return new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
