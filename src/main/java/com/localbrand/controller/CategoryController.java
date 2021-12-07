package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.CategoryParentDTO;
import com.localbrand.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_ALL)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> fillAllCategoryParent(@RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.findAllCategoryParent(page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_ALL_PARENT)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> fillAllCategory() {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.getAllCategoryParent();
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_SORT)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> fillAllCategoryParentByPropertiesAndSort(
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idStatus,
            @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.findAllByPropertiesAndSort(sort, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_BY_ID)
    public ResponseEntity<ServiceResult<CategoryParentDTO>> getCategoryParentById(@PathVariable Optional<Long> idCategory) {
        ServiceResult<CategoryParentDTO> result = this.categoryService.getByIdCategoryParent(idCategory);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_SAVE)
    public ResponseEntity<ServiceResult<CategoryParentDTO>> saveCategoryParent(@Valid @RequestBody CategoryParentDTO categoryParentDTO) {
        ServiceResult<CategoryParentDTO> result = this.categoryService.saveCategoryParent(categoryParentDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_DELETE)
    public ResponseEntity<ServiceResult<CategoryParentDTO>> deleteCategoryParent(@Valid @RequestBody CategoryParentDTO categoryParentDTO) {
        ServiceResult<CategoryParentDTO> result = this.categoryService.deleteCategoryParent(categoryParentDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_SEARCH)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> searchByNameCategoryParent(@RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.searchByNameCategoryParent(name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> findByStatusCategoryParent(@RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.findByStatusCategoryParent(idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    // Child

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_ALL)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> fillAllCategoryChild(@RequestParam Optional<Long> parentId, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.findAllCategoryChild(parentId, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_SORT)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> fillAllCategoryChildAndSort(@RequestParam Optional<Integer> sort, @RequestParam Optional<Long> parentId, @RequestParam Optional<Long> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.findAllCategoryChildAndSort(sort, parentId, idStatus, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_BY_ID)
    public ResponseEntity<ServiceResult<CategoryChildDTO>> getCategoryChildById(@PathVariable Optional<Long> idCategory) {
        ServiceResult<CategoryChildDTO> result = this.categoryService.getByIdCategoryChild(idCategory);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_SAVE)
    public ResponseEntity<ServiceResult<CategoryChildDTO>> saveCategoryChild(@Valid @RequestBody CategoryChildDTO categoryChildDTO) {
        ServiceResult<CategoryChildDTO> result = this.categoryService.saveCategoryChild(categoryChildDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_DELETE)
    public ResponseEntity<ServiceResult<CategoryChildDTO>> deleteCategoryChild(@Valid @RequestBody CategoryChildDTO categoryChildDTO) {
        ServiceResult<CategoryChildDTO> result = this.categoryService.deleteCategoryChild(categoryChildDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_SEARCH)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> searchByNameCategoryChild(@RequestParam String name, @RequestParam Optional<Long> parentId, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.searchByNameCategoryChild(name, parentId, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> findByStatusCategoryChild(@RequestParam Optional<Integer> idStatus ,@RequestParam Optional<Long> parentId, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.findByStatusCategoryChild(idStatus, parentId, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.CATEGORY_TO_SIZE)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> findCategoryToSize() {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.findAllCategoryParent();
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_BY_PARENT_ID)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> getAllCategoryByParentId(@PathVariable Optional<Integer> parentId){
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.getAllCategoryChildByParentId(parentId);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_ALL_BY_PARENT_ID)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> getAllCategoryByParent(@PathVariable Optional<Integer> parentId){
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.getAllCategoryChildByParent(parentId);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
