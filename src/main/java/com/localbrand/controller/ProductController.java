package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ProductRequestDTO;
import com.localbrand.dto.response.*;
import com.localbrand.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping(Interface_API.API.Product.PRODUCT_ADD)
    public ResponseEntity<ServiceResult<ProductResponseDTO>> addAll(HttpServletRequest request, @Valid @RequestBody ProductRequestDTO productRequestDTO){
        ServiceResult<ProductResponseDTO> result = this.productService.saveProduct(request, productRequestDTO);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_GET_LIST_PARENT)
    public ResponseEntity<ServiceResult<List<ProductParentResponseDTO>>> getAllParent(
           HttpServletRequest request,
           @RequestParam Optional<Integer> sort,
           @RequestParam Optional<Integer> idStatus,
           @RequestParam Optional<Integer> idCategoryParent,
           @RequestParam Optional<Integer> idCategoryChild,
           @RequestParam Optional<Integer> idGender,
           @RequestParam Optional<Integer> page
    ){
        ServiceResult<List<ProductParentResponseDTO>> result =
                this.productService.getAllParent(request, sort, idStatus, idCategoryParent, idCategoryChild, idGender, page);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_GET_LIST_CHILD)
    public ResponseEntity<ServiceResult<List<ProductChildResponseDTO>>> getAllParent(
            HttpServletRequest request,
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idProduct,
            @RequestParam Optional<Integer> idStatus,
            @RequestParam Optional<Integer> idColor,
            @RequestParam Optional<Integer> idSize,
            @RequestParam Optional<Integer> idTag,
            @RequestParam Optional<Integer> page
    ){
        ServiceResult<List<ProductChildResponseDTO>> result =
                this.productService.getAllChild(request, sort,
                        idProduct,
                        idStatus,
                        idColor,
                        idSize,
                        idTag,
                        page);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @DeleteMapping(Interface_API.API.Product.PRODUCT_DELETE_PARENT)
    public ResponseEntity<ServiceResult<ProductParentResponseDTO>> deleteParent(HttpServletRequest request, @RequestParam Optional<Long> idProduct){
        ServiceResult<ProductParentResponseDTO> result = this.productService.deleteProductParent(request, idProduct);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @DeleteMapping(Interface_API.API.Product.PRODUCT_DELETE_CHILD)
    public ResponseEntity<ServiceResult<ProductChildResponseDTO>> deleteChild(HttpServletRequest request, @RequestParam Optional<Long> idProductDetail){
        ServiceResult<ProductChildResponseDTO> result = this.productService.deleteProductChild(request, idProductDetail);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SHOW)
    public ResponseEntity<ServiceResult<ProductResponseShowAdminDTO>> showProduct(HttpServletRequest request, @RequestParam Optional<Long> idProduct){
        ServiceResult<ProductResponseShowAdminDTO> result = this.productService.showProduct(request, idProduct);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SEARCH)
    public ResponseEntity<ServiceResult<List<ProductParentResponseDTO>>> searchProduct(HttpServletRequest request, @RequestParam String name, @RequestParam Optional<Integer> page){
        ServiceResult<List<ProductParentResponseDTO>> result = this.productService.searchByName(request, name, page);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_GET_ALL_USER)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getListProductOnUser(
            HttpServletRequest request,
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idCategoryParent,
            @RequestParam Optional<Integer> idCategoryChild,
            @RequestParam Optional<Integer> idGender,
            @RequestParam Optional<Float> minPrice,
            @RequestParam Optional<Float> maxPrice,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getAllProductShowUser(
                request,
                sort,
                idCategoryParent,
                idCategoryChild,
                idGender,
                minPrice,
                maxPrice,
                page,
                limit
        );
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_SEARCH_USER)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> searchProductOnUser(HttpServletRequest request, @RequestParam String name, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.searchByNameOnUser(request, name, page, limit);
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
            HttpServletRequest request,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getAllProductOnUser(request, page, limit);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_NEW)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getProductNew(
            HttpServletRequest request,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getListNewProduct(request, limit);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_HOT)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getProductHot(
            HttpServletRequest request,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getListHotProduct(request, limit);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_RELATED)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getProductRelated(
            HttpServletRequest request,
            @RequestParam Optional<Integer> idCategory,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getListProductByCategory(request, idCategory,page,limit);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.Product.PRODUCT_LIKE)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getProductLike(
            HttpServletRequest request,
            @RequestParam Optional<Integer> idUser,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productService.getListProductLikeByUser(request, idUser,page,limit);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }
}
