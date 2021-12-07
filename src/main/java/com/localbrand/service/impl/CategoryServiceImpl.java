package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.CategoryParentDTO;
import com.localbrand.entity.Category;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.CategoryChildMapping;
import com.localbrand.model_mapping.Impl.CategoryParentMapping;
import com.localbrand.repository.CategoryRepository;
import com.localbrand.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  implements CategoryService{

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private final CategoryParentMapping categoryParentMapping;
    private final CategoryChildMapping categoryChildMapping;

    @Override
    public ServiceResult<List<CategoryChildDTO>> findAllCategoryParent() {
        this.log.info("Get list category parent");
        List<Category> listCategory = this.categoryRepository.findAllByIdStatusAndParentIdIsNull(Status_Enum.EXISTS.getCode());
        return new ServiceResult<>(HttpStatus.OK,Notification.Category.GET_LIST_PARENT_TO_SIZE, listCategory.stream().map(this.categoryChildMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<CategoryParentDTO>> findAllCategoryParent(Optional<Integer> page) {

        this.log.info("Get list category parent");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Category> listCategory = this.categoryRepository.findAllByParentIdIsNull(pageable).toList();

        List<CategoryParentDTO> listCategoryParent = new ArrayList<>();

        for (Category category: listCategory) {

            List<Category> listChildCategory = this.categoryRepository.findAllByParentId(category.getIdCategory().intValue(), PageRequest.of(0, 5)).toList();

            listCategoryParent.add(this.categoryParentMapping.toDto(category,listChildCategory));

        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.GET_LIST_CATEGORY_PARENT_SUCCESS, listCategoryParent);
    }

    @Override
    public ServiceResult<List<CategoryParentDTO>> findAllByPropertiesAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page) {

        this.log.info("Get list category with properties");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if (sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC, "nameCategory"));
        else if (sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC, "nameCategory"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Category> listCategory;

        if(idStatus.isEmpty() || idStatus.get() < 0)
            listCategory = this.categoryRepository.findAllByParentIdIsNull(pageable).toList();
        else
            listCategory = this.categoryRepository.findAllByParentIdIsNullAndIdStatus(idStatus.get(), pageable).toList();

        List<CategoryParentDTO> listCategoryParent = new ArrayList<>();

        for (Category category: listCategory) {

            List<Category> listChildCategory = this.categoryRepository.findAllByParentId(category.getIdCategory().intValue(), PageRequest.of(0,Config_Enum.SIZE_PAGE.getCode())).toList();

            listCategoryParent.add(this.categoryParentMapping.toDto(category,listChildCategory));

        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.GET_CATEGORY_PARENT_AND_SORT_SUCESS, listCategoryParent);

    }

    @Override
    public ServiceResult<CategoryParentDTO> getByIdCategoryParent(Optional<Long> idCategory) {

        this.log.info("Get category parent by id:");

        if (idCategory.isEmpty() || idCategory.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID, null);

        Category category = this.categoryRepository.findById(idCategory.get()).orElse(null);

        if(Objects.isNull(category))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Category.GET_CATEGORY_PARENT_BY_ID_FALSE, null);

        Pageable pageable1 = PageRequest.of(0,Config_Enum.SIZE_PAGE.getCode());

        List<Category> listChildCategory = this.categoryRepository.findAllByParentId(category.getIdCategory().intValue(), pageable1).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.GET_CATEGORY_PARENT_BY_ID_SUCCESS, this.categoryParentMapping.toDto(category,listChildCategory));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<CategoryParentDTO> saveCategoryParent(CategoryParentDTO categoryDTO) {

        this.log.info("Save Category Parent: "+categoryDTO);

        try {
                Category category = this.categoryParentMapping.toEntity(categoryDTO);

                Category categoryParentSave = this.categoryRepository.save(category);

                if (!Objects.isNull(categoryDTO.getListCategoryChildDTO())) {
                    if(!categoryDTO.getListCategoryChildDTO().isEmpty()) {
                        List<Category> listCategoryChild = categoryDTO.getListCategoryChildDTO().stream().map(categoryChild ->
                                this.categoryParentMapping.toEntity(categoryChild, categoryParentSave.getIdCategory().intValue())
                        ).collect(Collectors.toList());
                        this.categoryRepository.saveAll(listCategoryChild);
                    }
                }

                CategoryParentDTO categoryParentDTO = this.categoryParentMapping.toDto(categoryParentSave, this.categoryRepository.findAllByParentId(categoryParentSave.getIdCategory().intValue(), PageRequest.of(0,Config_Enum.SIZE_PAGE.getCode())).toList());
                return new ServiceResult<>(HttpStatus.OK, Notification.Category.SAVE_CATEGORY_PARENT_SUCCESS, categoryParentDTO);
        }catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Category.SAVE_CATEGORY_PARENT_FALSE, null);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<CategoryParentDTO> deleteCategoryParent(CategoryParentDTO categoryDTO) {

        this.log.info("Delete category parent");

        try {
                Category category = this.categoryParentMapping.toEntity(categoryDTO);

                category.setIdStatus(Status_Enum.DELETE.getCode());

                Category categoryParent = this.categoryRepository.save(category);

                List<Category> listCategoryChild = this.categoryRepository.findAllByParentIdAndIdStatus(categoryParent.getIdCategory().intValue(),Status_Enum.EXISTS.getCode());

                for (Category categoryChild: listCategoryChild) {
                    categoryChild.setIdStatus(Status_Enum.DELETE.getCode());

                    this.categoryRepository.save(categoryChild);
                }

                Pageable pageable1 = PageRequest.of(0,Config_Enum.SIZE_PAGE.getCode());

                List<Category> listChildCategory = this.categoryRepository.findAllByParentId(category.getIdCategory().intValue(), pageable1).toList();

                return new ServiceResult<>(HttpStatus.OK, Notification.Category.DELETE_CATEGORY_PARENT_SUCCESS, this.categoryParentMapping.toDto(categoryParent,listChildCategory));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Category.DELETE_CATEGORY_PARENT_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<CategoryParentDTO>> searchByNameCategoryParent(String nameCategory, Optional<Integer> page) {

        this.log.info("Get list category parent by name");

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Category> listCategory = this.categoryRepository.findAllByParentIdIsNullAndNameCategoryLike("%"+nameCategory+"%", pageable).toList();

        List<CategoryParentDTO> listCategoryParent = new ArrayList<>();

        Pageable pageable1 = PageRequest.of(0,Config_Enum.SIZE_PAGE.getCode());

        for (Category category: listCategory) {

            List<Category> listChildCategory = this.categoryRepository.findAllByParentId(category.getIdCategory().intValue(), pageable1).toList();

            listCategoryParent.add(this.categoryParentMapping.toDto(category,listChildCategory));

        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.SEARCH_CATEGORY_PARENT_SUCCESS, listCategoryParent);
    }

    @Override
    public ServiceResult<List<CategoryParentDTO>> findByStatusCategoryParent(Optional<Integer> idStatus, Optional<Integer> page) {

        this.log.info("Get list category parent by name");

        if(idStatus.isEmpty()
                || idStatus.get() < 1
                || idStatus.get() > 10
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_STATUS);

        if(page.isEmpty()
                || page.get() < 0
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Category> listCategory = this.categoryRepository.findAllByParentIdIsNullAndIdStatus(idStatus.get(), pageable).toList();

        List<CategoryParentDTO> listCategoryParent = new ArrayList<>();

        Pageable pageable1 = PageRequest.of(0,5);

        for (Category category: listCategory) {

            List<Category> listChildCategory = this.categoryRepository.findAllByParentId(category.getIdCategory().intValue(), pageable1).toList();

            listCategoryParent.add(this.categoryParentMapping.toDto(category,listChildCategory));

        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.FIND_CATEGORY_PARENT_BY_STATUS_SUCCESS, listCategoryParent);
    }

    @Override
    public ServiceResult<List<CategoryParentDTO>> getAllCategoryParent() {
        this.log.info("Get list category parent");
        List<Category> listCategory = this.categoryRepository.findAllByParentIdIsNull();
        return new ServiceResult<>(HttpStatus.OK,Notification.Category.GET_LIST_PARENT_TO_SIZE, listCategory.stream().map(this.categoryParentMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<CategoryChildDTO>> findAllCategoryChild(Optional<Long> parentId,Optional<Integer> page) {

        this.log.info("Get list category child by id category parent");

        if (parentId.isEmpty() || parentId.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID_PARENT, null);

        if(page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Category> listCategory = this.categoryRepository.findAllByParentId(parentId.get().intValue(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.GET_LIST_CATEGORY_CHILD_SUCCESS, listCategory.stream().map(categoryChildMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<CategoryChildDTO>> findAllCategoryChildAndSort(Optional<Integer> sort, Optional<Long> parentId, Optional<Long> idStatus, Optional<Integer> page) {

        this.log.info("Get list category child by id category parent and sort");

        if (parentId.isEmpty() || parentId.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID_PARENT, null);

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if (sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC, "nameCategory"));
        else if (sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC, "nameCategory"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Category> listCategory;

        if (idStatus.isEmpty()
            || idStatus.get() < 1)
            listCategory = this.categoryRepository.findAllByParentId(parentId.get().intValue(), pageable).toList();
        else
         listCategory = this.categoryRepository.findAllByParentIdAndIdStatus(parentId.get().intValue(), idStatus.get().intValue(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.GET_CATEGORY_CHILD_AND_SORT_SUCESS, listCategory.stream().map(categoryChildMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<CategoryChildDTO> getByIdCategoryChild(Optional<Long> idCategory) {

        if (idCategory.isEmpty() || idCategory.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID, null);

        Category category = this.categoryRepository.findById(idCategory.get()).orElse(null);

        if(Objects.isNull(category))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Category.GET_CATEGORY_CHILD_BY_ID_FALSE, null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.GET_CATEGORY_CHILD_BY_ID_SUCCESS, this.categoryChildMapping.toDto(category));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<CategoryChildDTO> saveCategoryChild(CategoryChildDTO categoryChildDTO) {

        this.log.info("Save category child: "+ categoryChildDTO);

        try {
                Category category = this.categoryChildMapping.toEntity(categoryChildDTO);

                Category categorySaved = this.categoryRepository.save(category);

                return new ServiceResult<>(HttpStatus.OK, Notification.Category.SAVE_CATEGORY_CHILD_SUCCESS, this.categoryChildMapping.toDto(categorySaved));
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Category.SAVE_CATEGORY_CHILD_FALSE, null);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<CategoryChildDTO> deleteCategoryChild(CategoryChildDTO categoryChildDTO) {
        this.log.info("Delete category child: "+ categoryChildDTO);

        try {

            if(Objects.isNull(categoryChildDTO.getIdCategory()))
                return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID, null);
            else{
                categoryChildDTO.setIdStatus(Status_Enum.DELETE.getCode());

                Category category = this.categoryChildMapping.toEntity(categoryChildDTO);

                Category categorySaved = this.categoryRepository.save(category);

                return new ServiceResult<>(HttpStatus.OK, Notification.Category.DELETE_CATEGORY_CHILD_SUCCESS, this.categoryChildMapping.toDto(categorySaved));
            }
        }catch (Exception ex){

            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Category.DELETE_CATEGORY_CHILD_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<CategoryChildDTO>> searchByNameCategoryChild(String nameCategory, Optional<Long> parentId, Optional<Integer> page) {

        this.log.info("Get list category child by name");

        if (parentId.isEmpty() || parentId.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID, null);

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Category> listCategory = this.categoryRepository.findAllByParentIdAndNameCategoryLike(parentId.get().intValue(),"%"+nameCategory+"%",pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.SEARCH_CATEGORY_CHILD_SUCCESS, listCategory.stream().map(categoryChildMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<CategoryChildDTO>> findByStatusCategoryChild(Optional<Integer> idStatus, Optional<Long> parentId, Optional<Integer> page) {
        this.log.info("Get list size by status and parent id");

        if(idStatus.isEmpty()
                || idStatus.get() < 1
                || idStatus.get() > 10
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_STATUS);

        if(page.isEmpty()
                || page.get() < 0
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID);

        if (parentId.isEmpty() || parentId.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_ID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Category> listCategory = this.categoryRepository.findAllByParentIdAndIdStatus(parentId.get().intValue(),idStatus.get(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.FIND_CATEGORY_CHILD_BY_STATUS_SUCCESS, listCategory.stream().map(categoryChildMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<CategoryChildDTO>> getAllCategoryChildByParentId(Optional<Integer> parentId) {

        this.log.info("Get list category child by parentId");

        if(Objects.isNull(parentId)
                || parentId.isEmpty()
                || parentId.get() < 0
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_STATUS);

        List<Category> listCategory = this.categoryRepository.findAllByParentIdAndIdStatus(parentId.orElse(0), Status_Enum.EXISTS.getCode());

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.GET_CATEGORY_CHILD_BY_PARENT_SUCCESS, listCategory.stream().map(this.categoryChildMapping::toDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<CategoryChildDTO>> getAllCategoryChildByParent(Optional<Integer> parentId) {

        this.log.info("Get list category child by parentId");

        if(Objects.isNull(parentId)
                || parentId.isEmpty()
                || parentId.get() < 0
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Category.Validate_Category.VALIDATE_STATUS);

        List<Category> listCategory = this.categoryRepository.findAllByParentId(parentId.orElse(0));

        return new ServiceResult<>(HttpStatus.OK, Notification.Category.GET_CATEGORY_CHILD_BY_PARENT_SUCCESS, listCategory.stream().map(this.categoryChildMapping::toDto).collect(Collectors.toList()));

    }
}
