package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ProductRequestDTO;
import com.localbrand.dto.response.*;
import com.localbrand.service.ProductService;
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
public class ProductController {

    private final ProductService productService;

    @PostMapping(Interface_API.API.Product.PRODUCT_ADD)
    public ResponseEntity<ServiceResult<ProductResponseDTO>> addAll(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        ServiceResult<ProductResponseDTO> result = this.productService.saveProduct(productRequestDTO);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_GET_LIST_PARENT)
    public ResponseEntity<ServiceResult<List<ProductParentResponseDTO>>> getAllParent(
           @RequestParam Optional<Integer> sort,
           @RequestParam Optional<Integer> idStatus,
           @RequestParam Optional<Integer> idCategoryParent,
           @RequestParam Optional<Integer> idCategoryChild,
           @RequestParam Optional<Integer> idGender,
           @RequestParam Optional<Integer> page
    ){
        ServiceResult<List<ProductParentResponseDTO>> result =
                this.productService.getAllParent(sort, idStatus, idCategoryParent, idCategoryChild, idGender, page);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_GET_LIST_CHILD)
    public ResponseEntity<ServiceResult<List<ProductChildResponseDTO>>> getAllParent(
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idProduct,
            @RequestParam Optional<Integer> idStatus,
            @RequestParam Optional<Integer> idColor,
            @RequestParam Optional<Integer> idSize,
            @RequestParam Optional<Integer> idTag,
            @RequestParam Optional<Integer> page
    ){
        ServiceResult<List<ProductChildResponseDTO>> result =
                this.productService.getAllChild(sort,
                        idProduct,
                        idStatus,
                        idColor,
                        idSize,
                        idTag,
                        page);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @DeleteMapping(Interface_API.API.Product.PRODUCT_DELETE_PARENT)
    public ResponseEntity<ServiceResult<ProductParentResponseDTO>> deleteParent(@RequestParam Optional<Long> idProduct){
        ServiceResult<ProductParentResponseDTO> result = this.productService.deleteProductParent(idProduct);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @DeleteMapping(Interface_API.API.Product.PRODUCT_DELETE_CHILD)
    public ResponseEntity<ServiceResult<ProductChildResponseDTO>> deleteChild(@RequestParam Optional<Long> idProductDetail){
        ServiceResult<ProductChildResponseDTO> result = this.productService.deleteProductChild(idProductDetail);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SHOW)
    public ResponseEntity<ServiceResult<ProductResponseShowAdminDTO>> showProduct(@RequestParam Optional<Long> idProduct){
        ServiceResult<ProductResponseShowAdminDTO> result = this.productService.showProduct(idProduct);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SEARCH)
    public ResponseEntity<ServiceResult<List<ProductParentResponseDTO>>> searchProduct(@RequestParam String name, @RequestParam Optional<Integer> page){
        ServiceResult<List<ProductParentResponseDTO>> result = this.productService.searchByName(name, page);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_GET_ALL_USER)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getListProductOnUser(
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idCategoryParent,
            @RequestParam Optional<Integer> idCategoryChild,
            @RequestParam Optional<Integer> idGender,
            @RequestParam Optional<Float> minPrice,
            @RequestParam Optional<Float> maxPrice,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Long> userId
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getAllProductShowUser(
                sort,
                idCategoryParent,
                idCategoryChild,
                idGender,
                minPrice,
                maxPrice,
                page,
                limit,
                userId
        );
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SEARCH_USER)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> searchProductOnUser(@RequestParam String name, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Long> userId){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.searchByNameOnUser(name, page, limit, userId);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SHOW_USER)
    public ResponseEntity<ServiceResult<ProductShowUserResponseDTO>> showDetailOnUser(@RequestParam Optional<Long> idProduct){
        ServiceResult<ProductShowUserResponseDTO> result = this.productService.showProductOnUser(idProduct);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SHOW_USER_BY_COLOR)
    public ResponseEntity<ServiceResult<ProductFilterColorResponseDTO>> showDetailOnUserByColor(@RequestParam Optional<Long> idProduct, @RequestParam Optional<Long> idColor){
        ServiceResult<ProductFilterColorResponseDTO> result = this.productService.findProductUserByColor(idProduct, idColor);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SHOW_USER_BY_COLOR_AND_SIZE)
    public ResponseEntity<ServiceResult<ProductDetailUserDTO>> showDetailOnUserByColorAndSize(@RequestParam Optional<Long> idProduct, @RequestParam Optional<Integer> idColor, @RequestParam Optional<Integer> idSize){
        ServiceResult<ProductDetailUserDTO> result = this.productService.findByIdProductAndIdColorAndIdSize(idProduct, idColor, idSize);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SHOW_ALL)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getAllShowUser(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Long> userId
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getAllProductOnUser(page, limit, userId);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_NEW)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getProductNew(
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Long> userId
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getListNewProduct(limit, userId);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_HOT)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getProductHot(
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Long> userId
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getListHotProduct(limit, userId);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_RELATED)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getProductRelated(
            @RequestParam Optional<Integer> idCategory,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Long> userId
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getListProductByCategory(idCategory,page,limit, userId);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_LIKE)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getProductLike(
            @RequestParam Optional<Integer> idUser,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getListProductLikeByUser(idUser,page,limit);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }
}
