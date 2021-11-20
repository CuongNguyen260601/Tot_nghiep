package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.ColorDTO;
import com.localbrand.entity.Color;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.ColorMapping;
import com.localbrand.repository.ColorRepository;
import com.localbrand.service.ColorService;
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
public class ColorServiceImpl implements ColorService{
    private final Logger log = LoggerFactory.getLogger(SizeServiceImpl.class);

    private final ColorRepository colorRepository;
    private final ColorMapping colorMapping;
    private final Role_Utils role_utils;


    @Override
    public ServiceResult<List<ColorDTO>> findAll(HttpServletRequest request, Optional<Integer> page) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COLOR.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
        }

        this.log.info("Get list color with page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Color> listColor = this.colorRepository.findAll(pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Color.GET_LIST_COLOR_SUCCESS, listColor.stream().map(this.colorMapping::toDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<List<ColorDTO>> getAll() {
        List<Color> listColor = this.colorRepository.findAll();

        return new ServiceResult<>(HttpStatus.OK, Notification.Color.GET_LIST_COLOR_SUCCESS, listColor.stream().map(this.colorMapping::toDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<List<ColorDTO>> findAllAndSort(HttpServletRequest request, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page) {
        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COLOR.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
        }

        this.log.info("Get list color with page and sort");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if (sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC, "nameColor"));
        else if (sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC, "nameColor"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Color> listColor;

        if(idStatus.isEmpty()
            || idStatus.get() < 1)
            listColor = this.colorRepository.findAll(pageable).toList();
        else
            listColor = this.colorRepository.findAllByIdStatus(idStatus.get(),pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Color.GET_COLOR_AND_SORT_SUCESS, listColor.stream().map(this.colorMapping::toDto).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<ColorDTO> getById(HttpServletRequest request, Optional<Long> idColor) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COLOR.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
        }

        this.log.info("Get color by id");

        if (idColor.isEmpty() || idColor.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Color.Validate_Color.VALIDATE_ID, null);

        Color color = this.colorRepository.findById(idColor.orElse(1L)).orElse(null);

        if (Objects.isNull(color))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Color.GET_COLOR_BY_ID_FALSE, null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Color.GET_COLOR_BY_ID_SUCCESS,this.colorMapping.toDto(color));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<ColorDTO> save(HttpServletRequest request, ColorDTO colorDTO) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COLOR.getModule(), Action_Enum.SAVE.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save color", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not save color", null);
        }

        this.log.info("Save color: "+ colorDTO);

        try {
            Color color = this.colorMapping.toEntity(colorDTO);

            Color colorSaved = this.colorRepository.save(color);

            return new ServiceResult<>(HttpStatus.OK, Notification.Color.SAVE_COLOR_SUCCESS, this.colorMapping.toDto(colorSaved));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Color.SAVE_COLOR_FALSE, null);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<ColorDTO> delete(HttpServletRequest request, ColorDTO colorDTO) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COLOR.getModule(), Action_Enum.DELETE.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not delete color", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not delete color", null);
        }

        this.log.info("Delete color: "+ colorDTO);

        try {
            colorDTO.setIdStatus(Status_Enum.DELETE.getCode());

            Color color = this.colorMapping.toEntity(colorDTO);

            Color colorDelete = this.colorRepository.save(color);

            return new ServiceResult<>(HttpStatus.OK, Notification.Color.DELETE_COLOR_SUCCESS, this.colorMapping.toDto(colorDelete));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Color.DELETE_COLOR_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<ColorDTO>> searchByName(HttpServletRequest request, String nameColor, Optional<Integer> page) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COLOR.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
        }

        this.log.info("Get list color by name and page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Color> listColor = this.colorRepository.findAllByNameColorLike("%"+nameColor+"%",pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Color.SEARCH_COLOR_SUCCESS, listColor.stream().map(this.colorMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<ColorDTO>> findByStatus(HttpServletRequest request, Optional<Integer> idStatus, Optional<Integer> page) {

        Object email = request.getAttribute("USER_NAME");

        if(Objects.nonNull(email)){
            Boolean checkRole = role_utils.checkRole(email.toString(), Module_Enum.COLOR.getModule(), Action_Enum.READ.getAction());
            if(!checkRole){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
            }
        }else{
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You can not get color", null);
        }

        this.log.info("Get list color by status and page");

        if(idStatus.isEmpty()
                || idStatus.get() < 1
                || idStatus.get() > 10
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Color.Validate_Color.VALIDATE_STATUS);

        if(page.isEmpty()
                || page.get() < 0
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Color> listColor = this.colorRepository.findAllByIdStatus(idStatus.orElse(2),pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Color.FIND_COLOR_BY_STATUS_SUCCESS, listColor.stream().map(this.colorMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<ColorDTO>> findAllExists() {

        this.log.info("Get all color exists");

        List<Color> listColor = this.colorRepository.getAllByIdStatus(Status_Enum.EXISTS.getCode());

        return new ServiceResult<>(HttpStatus.OK, Notification.Color.FIND_COLOR_EXISTS, listColor.stream().map(this.colorMapping::toDto).collect(Collectors.toList()));
    }

}
