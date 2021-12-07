package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.SizeDTO;
import com.localbrand.dto.response.SizeResponseDTO;
import com.localbrand.entity.Size;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.SizeMapping;
import com.localbrand.repository.SizeRepository;
import com.localbrand.service.SizeService;
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
public class  SizeServiceImpl implements SizeService {

    private final Logger log = LoggerFactory.getLogger(SizeServiceImpl.class);

    private final SizeRepository sizeRepository;
    private final SizeMapping sizeMapping;

    @Override
    public ServiceResult<List<SizeResponseDTO>> findAll(Optional<Integer> page) {

        this.log.info("Get list size with page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Size> listSize = this.sizeRepository.findAll(pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Size.GET_LIST_SIZE_SUCCESS, listSize.stream().map(this.sizeMapping::toDtoResponse).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<List<SizeResponseDTO>> findAllAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> idCategory, Optional<Integer> page) {

        this.log.info("Get list size with page and sort");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if (sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC, "nameSize"));
        else if (sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC, "nameSize"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Size> listSize;

        if(idStatus.isEmpty()
            || idStatus.get() < 1){

            if(idCategory.isEmpty()
                    || idCategory.get() < 1)
                listSize = this.sizeRepository.findAll(pageable).toList();
            else
                listSize = this.sizeRepository.findAllByIdCategory(idCategory.get(), pageable).toList();

        }else{
            if(idCategory.isEmpty()
                    || idCategory.get() < 1)
                listSize = this.sizeRepository.findAllByIdStatus(idStatus.get() ,pageable).toList();
            else
                listSize = this.sizeRepository.findAllByIdStatusAndIdCategory( idStatus.get(), idCategory.get(), pageable).toList();

        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Size.GET_SIZE_AND_SORT_SUCCESS, listSize.stream().map(this.sizeMapping::toDtoResponse).collect(Collectors.toList()));

    }

    @Override
    public ServiceResult<SizeResponseDTO> getById(Optional<Long> idSize) {

        this.log.info("Get size by id");

        if (idSize.isEmpty() || idSize.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Size.Validate_Size.VALIDATE_ID, null);

        Size size = this.sizeRepository.findById(idSize.orElse(1L)).orElse(null);

        if (Objects.isNull(size))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Size.GET_SIZE_BY_ID_FALSE, null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Size.GET_SIZE_BY_ID_SUCCESS,this.sizeMapping.toDtoResponse(size));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<SizeResponseDTO> save(SizeDTO sizeDTO) {

        this.log.info("Save size: "+ sizeDTO);

        try {
                Size size = this.sizeMapping.toEntity(sizeDTO);

                Size sizeSaved = this.sizeRepository.save(size);

                return new ServiceResult<>(HttpStatus.OK, Notification.Size.SAVE_SIZE_SUCCESS, this.sizeMapping.toDtoResponse(sizeSaved));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Size.SAVE_SIZE_FALSE, null);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<SizeResponseDTO> delete(SizeDTO sizeDTO) {

        this.log.info("Delete size: "+ sizeDTO);

        try {
                sizeDTO.setIdStatus(Status_Enum.DELETE.getCode());

                Size size = this.sizeMapping.toEntity(sizeDTO);

                Size sizeDelete = this.sizeRepository.save(size);

                return new ServiceResult<>(HttpStatus.OK, Notification.Size.DELETE_SIZE_SUCCESS, this.sizeMapping.toDtoResponse(sizeDelete));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Size.DELETE_SIZE_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<SizeResponseDTO>> searchByName(String nameSize, Optional<Integer> page) {

        this.log.info("Get list size by name and page");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Size> listSize = this.sizeRepository.findAllByNameSizeLike("%"+nameSize+"%",pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Size.SEARCH_SIZE_SUCCESS, listSize.stream().map(this.sizeMapping::toDtoResponse).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<SizeResponseDTO>> findByStatus(Optional<Integer> idStatus, Optional<Integer> page) {

        this.log.info("Get list size by status and page");

        if(idStatus.isEmpty()
           || idStatus.get() < 1
           || idStatus.get() > 10
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Size.Validate_Size.VALIDATE_STATUS);

        if(page.isEmpty()
           || page.get() < 0
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Size> listSize = this.sizeRepository.findAllByIdStatus(idStatus.orElse(2),pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Size.FIND_SIZE_BY_STATUS_SUCCESS, listSize.stream().map(this.sizeMapping::toDtoResponse).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<SizeResponseDTO>> getSizeByIdCategory(Optional<Integer> idCategory) {
        if(Objects.isNull(idCategory)
                || idCategory.isEmpty()
                || idCategory.get() < 1
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID);

        List<Size> listSize = this.sizeRepository.findAllByIdCategoryAndIdStatus(idCategory.get(), Status_Enum.EXISTS.getCode());

        return new ServiceResult<>(HttpStatus.OK, Notification.Size.FIND_SIZE_BY_STATUS_SUCCESS, listSize.stream().map(this.sizeMapping::toDtoResponse).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<SizeResponseDTO>> getSizeByCategory(Optional<Integer> idCategory) {
        if(Objects.isNull(idCategory)
                || idCategory.isEmpty()
                || idCategory.get() < 1
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID);

        List<Size> listSize = this.sizeRepository.findAllByIdCategory(idCategory.get());

        return new ServiceResult<>(HttpStatus.OK, Notification.Size.FIND_SIZE_BY_STATUS_SUCCESS, listSize.stream().map(this.sizeMapping::toDtoResponse).collect(Collectors.toList()));
    }
}
