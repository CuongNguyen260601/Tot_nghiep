package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.CategoryParentDTO;
import com.localbrand.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_ALL)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> fillAllCategoryParent(HttpServletRequest request, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.findAllCategoryParent(request, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_SORT)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> fillAllCategoryParentByPropertiesAndSort(
            HttpServletRequest request,
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idStatus,
            @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.findAllByPropertiesAndSort(request, sort, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_BY_ID)
    public ResponseEntity<ServiceResult<CategoryParentDTO>> getCategoryParentById(HttpServletRequest request, @PathVariable Optional<Long> idCategory) {
        ServiceResult<CategoryParentDTO> result = this.categoryService.getByIdCategoryParent(request, idCategory);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_SAVE)
    public ResponseEntity<ServiceResult<CategoryParentDTO>> saveCategoryParent(HttpServletRequest request, @Valid @RequestBody CategoryParentDTO categoryParentDTO) {
        ServiceResult<CategoryParentDTO> result = this.categoryService.saveCategoryParent(request, categoryParentDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_DELETE)
    public ResponseEntity<ServiceResult<CategoryParentDTO>> deleteCategoryParent(HttpServletRequest request, @Valid @RequestBody CategoryParentDTO categoryParentDTO) {
        ServiceResult<CategoryParentDTO> result = this.categoryService.deleteCategoryParent(request, categoryParentDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_SEARCH)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> searchByNameCategoryParent(HttpServletRequest request, @RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.searchByNameCategoryParent(request, name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Parent.CATEGORY_PARENT_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<CategoryParentDTO>>> findByStatusCategoryParent(HttpServletRequest request, @RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryParentDTO>> result = this.categoryService.findByStatusCategoryParent(request, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    // Child

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_ALL)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> fillAllCategoryChild(HttpServletRequest request, @RequestParam Optional<Long> parentId, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.findAllCategoryChild(request, parentId, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_SORT)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> fillAllCategoryChildAndSort(HttpServletRequest request, @RequestParam Optional<Integer> sort, @RequestParam Optional<Long> parentId, @RequestParam Optional<Long> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.findAllCategoryChildAndSort(request, sort, parentId, idStatus, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_BY_ID)
    public ResponseEntity<ServiceResult<CategoryChildDTO>> getCategoryChildById(HttpServletRequest request, @PathVariable Optional<Long> idCategory) {
        ServiceResult<CategoryChildDTO> result = this.categoryService.getByIdCategoryChild(request, idCategory);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_SAVE)
    public ResponseEntity<ServiceResult<CategoryChildDTO>> saveCategoryChild(HttpServletRequest request, @Valid @RequestBody CategoryChildDTO categoryChildDTO) {
        ServiceResult<CategoryChildDTO> result = this.categoryService.saveCategoryChild(request, categoryChildDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_DELETE)
    public ResponseEntity<ServiceResult<CategoryChildDTO>> deleteCategoryChild(HttpServletRequest request, @Valid @RequestBody CategoryChildDTO categoryChildDTO) {
        ServiceResult<CategoryChildDTO> result = this.categoryService.deleteCategoryChild(request, categoryChildDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_SEARCH)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> searchByNameCategoryChild(HttpServletRequest request, @RequestParam String name, @RequestParam Optional<Long> parentId, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.searchByNameCategoryChild(request, name, parentId, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> findByStatusCategoryChild(HttpServletRequest request, @RequestParam Optional<Integer> idStatus ,@RequestParam Optional<Long> parentId, @RequestParam Optional<Integer> page) {
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.findByStatusCategoryChild(request, idStatus, parentId, page);
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
    public ResponseEntity<ServiceResult<List<CategoryChildDTO>>> getAllCategoryByParentId(HttpServletRequest request, @PathVariable Optional<Integer> parentId){
        ServiceResult<List<CategoryChildDTO>> result = this.categoryService.getAllCategoryChildByParentId(request, parentId);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
