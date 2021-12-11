package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.request.ProductRequestDTO;
import com.localbrand.dto.response.*;
import com.localbrand.entity.Color;
import com.localbrand.entity.Product;
import com.localbrand.entity.ProductDetail;
import com.localbrand.entity.User;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.ProductMapping;
import com.localbrand.repository.ColorRepository;
import com.localbrand.repository.ProductDetailRepository;
import com.localbrand.repository.ProductRepository;
import com.localbrand.repository.UserRepository;
import com.localbrand.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapping productMapping;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ColorRepository colorRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<ProductResponseDTO> saveProduct(ProductRequestDTO productRequestDTO) {

        Product product = this.productMapping.toEntity(productRequestDTO);

        List<ProductDetail> productDetailList = this.productMapping.toListProductDetail(product, productRequestDTO);

        product = this.productRepository.save(product);

        for(ProductDetail productDetail: productDetailList){
            productDetail.setIdProduct(product.getIdProduct().intValue());
        }

        this.productDetailRepository.saveAll(productDetailList);

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.SAVE_PRODUCT_SUCCESS, this.productMapping.toProductResponseDTO(product));
    }

    @Override
    public ServiceResult<List<ProductParentResponseDTO>> getAllParent(Optional<Integer> sort,
                                                                      Optional<Integer> idStatus,
                                                                      Optional<Integer> idCategoryParent,
                                                                      Optional<Integer> idCategoryChild,
                                                                      Optional<Integer> idGender,
                                                                      Optional<Integer> page) {

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if(sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC, "nameProduct"));
        else if(sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC, "nameProduct"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Product> listProduct = new ArrayList<>();

        if(idStatus.isEmpty() || idStatus.get() < 1){
            if(idCategoryParent.isEmpty() || idCategoryParent.get() < 1){
                if(idCategoryChild.isEmpty() || idCategoryChild.get() < 1){
                    if(idGender.isEmpty() || idGender.get() <1){
                        listProduct = this.productRepository.findAll(pageable).toList();
                    }else{
                        listProduct = this.productRepository.findAllByIdGender(idGender.get(), pageable).toList();
                    }
                }else{
                    if(idGender.isEmpty() || idGender.get() <1){
                        listProduct = this.productRepository.findAllByIdCategoryChild(idCategoryChild.get(), pageable).toList();
                    }else{
                        listProduct = this.productRepository.findAllByIdCategoryChildAndIdGender(idCategoryChild.get(), idGender.get(), pageable).toList();
                    }
                }
            }else{
                if(idCategoryChild.isEmpty() || idCategoryChild.get() < 1){
                    if(idGender.isEmpty() || idGender.get() <1){
                        listProduct = this.productRepository.findAllByIdCategoryParent(idCategoryParent.get(),pageable).toList();
                    }else{
                        listProduct = this.productRepository.findAllByIdCategoryParentAndIdGender(idCategoryParent.get(), idGender.get(), pageable).toList();
                    }
                }else{
                    if(idGender.isEmpty() || idGender.get() <1){
                        listProduct = this.productRepository.findAllByIdCategoryChild(idCategoryChild.get(), pageable).toList();
                    }else{
                        listProduct = this.productRepository.findAllByIdCategoryChildAndIdGender(idCategoryChild.get(), idGender.get(), pageable).toList();
                    }
                }
            }
        }else{
            if(idCategoryParent.isEmpty() || idCategoryParent.get() < 1){
                if(idCategoryChild.isEmpty() || idCategoryChild.get() < 1){
                    if(idGender.isEmpty() || idGender.get() <1){
                        listProduct = this.productRepository.findAllByIdStatus(idStatus.get(),pageable).toList();
                    }else{
                        listProduct = this.productRepository.findAllByIdStatusAndIdGender(idStatus.get(), idGender.get(), pageable).toList();
                    }
                }else{
                    if(idGender.isEmpty() || idGender.get() <1){
                        listProduct = this.productRepository.findAllByIdCategoryChildAndIdStatus(idCategoryChild.get(), idStatus.get(), pageable).toList();
                    }else{
                        listProduct = this.productRepository.findAllByIdCategoryChildAndIdGenderAndIdStatus(idCategoryChild.get(), idGender.get(), idStatus.get(),pageable).toList();
                    }
                }
            }else{
                if(idCategoryChild.isEmpty() || idCategoryChild.get() < 1){
                    if(idGender.isEmpty() || idGender.get() <1){
                        listProduct = this.productRepository.findAllByIdCategoryParentAndIdStatus(idCategoryParent.get(), idStatus.get() ,pageable).toList();
                    }else{
                        listProduct = this.productRepository.findAllByIdCategoryParentAndIdGenderAndIdStatus(idCategoryParent.get(), idGender.get(), idStatus.get(), pageable).toList();
                    }
                }else{
                    if(idGender.isEmpty() || idGender.get() <1){
                        listProduct = this.productRepository.findAllByIdCategoryChildAndIdStatus(idCategoryChild.get(), idStatus.get(), pageable).toList();
                    }else{
                        listProduct = this.productRepository.findAllByIdCategoryChildAndIdGenderAndIdStatus(idCategoryChild.get(), idGender.get(), idStatus.get(),pageable).toList();
                    }
                }
            }
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_PARENT, listProduct
                .stream()
                .map(this.productMapping::toProductParent).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<ProductChildResponseDTO>> getAllChild(Optional<Integer> sort,
                                                                    Optional<Integer> idProduct,
                                                                    Optional<Integer> idStatus,
                                                                    Optional<Integer> idColor,
                                                                    Optional<Integer> idSize,
                                                                    Optional<Integer> idTag,
                                                                    Optional<Integer> page) {

        if (page.isEmpty()
                || page.get() < 0
                || idProduct.isEmpty()
                || idProduct.get() < 0) return new ServiceResult<>(HttpStatus.OK, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if(sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC, "price"));
        else if(sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC, "price"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<ProductDetail> listProductDetail = new ArrayList<>();

        if(idStatus.isEmpty() || idStatus.get() < 1){
            if(idColor.isEmpty() || idColor.get() < 1){
                if(idSize.isEmpty() || idSize.get() < 1){
                    if(idTag.isEmpty() || idTag.get() < 1){
                        listProductDetail = this.productDetailRepository.findAllByIdProduct(idProduct.get(), pageable).toList();
                    }else{
                        listProductDetail = this.productDetailRepository.findAllByIdTag(idProduct.get(), idTag.get(), pageable).toList();
                    }
                }else{
                    if(idTag.isEmpty() || idTag.get() < 1){
                        listProductDetail = this.productDetailRepository.findAllByIdProductAndIdSize(idProduct.get(), idSize.get(), pageable).toList();
                    }else{
                        listProductDetail = this.productDetailRepository.findAllByIdTagAndIdSize(idProduct.get(), idTag.get(), idSize.get(), pageable).toList();
                    }
                }
            }else {
                if(idSize.isEmpty() || idSize.get() < 1){
                    if(idTag.isEmpty() || idTag.get() < 1){
                        listProductDetail = this.productDetailRepository.findAllByIdProductAndIdColor(idProduct.get(), idColor.get(), pageable).toList();
                    }else{
                        listProductDetail = this.productDetailRepository.findAllByIdTagAndIdColor(idProduct.get(), idTag.get(), idColor.get(), pageable).toList();
                    }
                }else{
                    if(idTag.isEmpty() || idTag.get() < 1){
                        listProductDetail = this.productDetailRepository.findAllByIdProductAndIdSizeAndIdColor(idProduct.get(), idSize.get(), idColor.get(),pageable).toList();
                    }else{
                        listProductDetail = this.productDetailRepository.findAllByIdTagAndIdSizeAndIdColor(idProduct.get(), idTag.get(), idSize.get(), idColor.get(), pageable).toList();
                    }
                }
            }
        }else{
            if(idColor.isEmpty() || idColor.get() < 1){
                if(idSize.isEmpty() || idSize.get() < 1){
                    if(idTag.isEmpty() || idTag.get() < 1){
                        listProductDetail = this.productDetailRepository.findAllByIdProductAndIdStatus(idProduct.get(), idStatus.get(), pageable).toList();
                    }else{
                        listProductDetail = this.productDetailRepository.findAllByIdTagAndIdStatus(idProduct.get(), idTag.get(), idStatus.get(),pageable).toList();
                    }
                }else{
                    if(idTag.isEmpty() || idTag.get() < 1){
                        listProductDetail = this.productDetailRepository.findAllByIdProductAndIdSizeAndIdStatus(idProduct.get(), idSize.get(), idStatus.get(), pageable).toList();
                    }else{
                        listProductDetail = this.productDetailRepository.findAllByIdTagAndIdSizeAndIdStatus(idProduct.get(), idTag.get(), idSize.get(), idStatus.get(), pageable).toList();
                    }
                }
            }else {
                if(idSize.isEmpty() || idSize.get() < 1){
                    if(idTag.isEmpty() || idTag.get() < 1){
                        listProductDetail = this.productDetailRepository.findAllByIdProductAndIdColorAndIdStatus(idProduct.get(), idColor.get(), idStatus.get(), pageable).toList();
                    }else{
                        listProductDetail = this.productDetailRepository.findAllByIdTagAndIdColorAndIdStatus(idProduct.get(), idTag.get(), idColor.get(), idStatus.get(), pageable).toList();
                    }
                }else{
                    if(idTag.isEmpty() || idTag.get() < 1){
                        listProductDetail = this.productDetailRepository.findAllByIdProductAndIdSizeAndIdColorAndIdStatus(idProduct.get(), idSize.get(), idColor.get(), idStatus.get(),pageable).toList();
                    }else{
                        listProductDetail = this.productDetailRepository.findAllByIdTagAndIdSizeAndIdColorAndIdStatus(idProduct.get(), idTag.get(), idSize.get(), idColor.get(), idStatus.get(), pageable).toList();
                    }
                }
            }
        }
        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_CHILD, listProductDetail.stream().map(this.productMapping::toProductChild).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<ProductParentResponseDTO>> searchByName(String name, Optional<Integer> page) {

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<Product> listProduct = this.productRepository.findAllByNameProductLike("%"+name+"%", pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.SEARCH_PRODUCT_BY_NAME_SUCCESS, listProduct.stream().map(this.productMapping::toProductParent).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<ProductResponseShowAdminDTO> showProduct(Optional<Long> idProduct) {

        Product product = this.productRepository.findById(idProduct.get()).orElse(null);

        if(Objects.isNull(product))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Product.SHOW_PRODUCT_IS_FALSE, null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.SHOW_PRODUCT_IS_SUCCESS ,this.productMapping.toProductResponseShowDTO(product));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<ProductParentResponseDTO> deleteProductParent(Optional<Long> idProduct) {

        Product product = this.productRepository.findById(idProduct.get()).orElse(null);

        if(Objects.isNull(product))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Product.DELETE_PRODUCT_IS_FALSE, null);

        if(product.getIdStatus().equals(Status_Enum.DELETE.getCode()))
            product.setIdStatus(Status_Enum.EXISTS.getCode());
        else
            product.setIdStatus(Status_Enum.DELETE.getCode());

        product = this.productRepository.save(product);

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.DELETE_PRODUCT_IS_SUCCESS, this.productMapping.toProductParent(product));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServiceResult<ProductChildResponseDTO> deleteProductChild(Optional<Long> idProductDetail) {

        ProductDetail productDetail = this.productDetailRepository.findById(idProductDetail.get()).orElse(null);

        if(Objects.isNull(productDetail))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.Product.DELETE_PRODUCT_DETAIL_IS_FALSE, null);

        if(productDetail.getIdStatus().equals(Status_Enum.DELETE.getCode()))
            productDetail.setIdStatus(Status_Enum.EXISTS.getCode());
        else
            productDetail.setIdStatus(Status_Enum.DELETE.getCode());

        productDetail = this.productDetailRepository.save(productDetail);

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.DELETE_PRODUCT_DETAIL_IS_SUCCESS, this.productMapping.toProductChild(productDetail));
    }

    @Override
    public ServiceResult<List<ProductShowUserResponseDTO>> getAllProductOnUser(Optional<Integer> page, Optional<Integer> limit,  Optional<Long> userId) {

        User user;

        if(userId.isEmpty() || userId.get() < 1){
            user = null;
        }else{
            user = this.userRepository.findById(userId.get()).orElse(null);
        }

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());
        List<Product> listProduct = this.productRepository.findAllByIdStatus(Status_Enum.EXISTS.getCode(), pageable).toList();

        List<ProductShowUserResponseDTO> listProductShowUserResponseDTOS;

        if(Objects.isNull(user)){
            listProductShowUserResponseDTOS = listProduct.stream().map(this.productMapping::toProductShowUser).collect(Collectors.toList());
        }else {
            listProductShowUserResponseDTOS = listProduct.stream().map(product -> this.productMapping.toProductShowUserAndLike(product, user)).collect(Collectors.toList());
        }
        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_ON_USER_SUCCESS, listProductShowUserResponseDTOS);

    }

    @Override
    public ServiceResult<List<ProductShowUserResponseDTO>> getAllProductShowUser(Optional<Integer> sort, Optional<Integer> idCategoryParent, Optional<Integer> idCategoryChild, Optional<Integer> idGender, Optional<Float> minPrice, Optional<Float> maxPrice, Optional<Integer> page, Optional<Integer> limit, Optional<Long> userId) {

        User user;

        if(userId.isEmpty() || userId.get() < 1){
            user = null;
        }else{
            user = this.userRepository.findById(userId.get()).orElse(null);
        }

        if (page.isEmpty()
                || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<Product> listProduct;

        if (idCategoryParent.isEmpty() || idCategoryParent.get() < 1 ){
            if(idCategoryChild.isEmpty() || idCategoryChild.get() <1 ){
                if(idGender.isEmpty() || idGender.get() < 1 ){
                    listProduct = this.productRepository.findAllByIdStatus(Status_Enum.EXISTS.getCode(), minPrice.get(), maxPrice.get(), pageable).toList();
                }else{
                    listProduct = this.productRepository.findAllByIdGenderAndIdStatus(idGender.get(), Status_Enum.EXISTS.getCode(), minPrice.get(), maxPrice.get(), pageable).toList();
                }
            }else{
                if(idGender.isEmpty() || idGender.get() < 1 ){
                    listProduct = this.productRepository.findAllByIdCategoryAndIdStatus(idCategoryChild.get(), Status_Enum.EXISTS.getCode(), minPrice.get(), maxPrice.get(),  pageable).toList();
                }else{
                    listProduct = this.productRepository.findAllByIdCategoryAndIdStatusAndIdGender(idGender.get(), Status_Enum.EXISTS.getCode(), idGender.get(), minPrice.get(), maxPrice.get(), pageable).toList();
                }
            }
        }else{
            if(idCategoryChild.isEmpty() || idCategoryChild.get() <1 ){
                if(idGender.isEmpty() || idGender.get() < 1 ){
                    listProduct = this.productRepository.findAllByIdStatusAndIdCategoryParent( idCategoryParent.get(), Status_Enum.EXISTS.getCode(), pageable).toList();
                }else{
                    listProduct = this.productRepository.findAllByIdGenderAndIdStatusAndIdCategoryParent(idGender.get(), Status_Enum.EXISTS.getCode(), idCategoryParent.get(), pageable).toList();
                }
            }else{
                if(idGender.isEmpty() || idGender.get() < 1 ){
                    listProduct = this.productRepository.findAllByIdCategoryAndIdStatus(idCategoryChild.get(), Status_Enum.EXISTS.getCode(), minPrice.get(), maxPrice.get(),  pageable).toList();
                }else{
                    listProduct = this.productRepository.findAllByIdCategoryAndIdStatusAndIdGender(idCategoryChild.get(), Status_Enum.EXISTS.getCode(), idGender.get(), minPrice.get(), maxPrice.get(),pageable).toList();
                }
            }
        }

        List<ProductShowUserResponseDTO> listProductShowUserResponseDTOS;

        if(Objects.isNull(user)){
            listProductShowUserResponseDTOS = listProduct.stream().map(this.productMapping::toProductShowUser).collect(Collectors.toList());
        }else {
            listProductShowUserResponseDTOS = listProduct.stream().map(product -> this.productMapping.toProductShowUserAndLike(product, user)).collect(Collectors.toList());
        }

        if(sort.get().equals(0)){
            listProductShowUserResponseDTOS.sort(new Comparator<ProductShowUserResponseDTO>() {
                @Override
                public int compare(ProductShowUserResponseDTO o1, ProductShowUserResponseDTO o2) {
                    if(o1.getMaxPrice()<o2.getMaxPrice()) return -1;
                    if(o1.getMaxPrice()>o2.getMaxPrice()) return 1;
                    if(o1.getMaxPrice().equals(o2.getMaxPrice()))
                        return o1.getNameProduct().compareTo(o2.getNameProduct());
                    return 0;
                }
            });
        }else if(sort.get().equals(1)){
            listProductShowUserResponseDTOS.sort(new Comparator<ProductShowUserResponseDTO>() {
                @Override
                public int compare(ProductShowUserResponseDTO o1, ProductShowUserResponseDTO o2) {
                    if(o1.getMaxPrice()<o2.getMaxPrice()) return 1;
                    if(o1.getMaxPrice()>o2.getMaxPrice()) return -1;
                    if(o1.getMaxPrice().equals(o2.getMaxPrice()))
                        return o1.getNameProduct().compareTo(o2.getNameProduct());
                    return 0;
                }
            });
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_ON_USER_SUCCESS, listProductShowUserResponseDTOS);
    }

    @Override
    public ServiceResult<List<ProductShowUserResponseDTO>> searchByNameOnUser(String name, Optional<Integer> page, Optional<Integer> limit, Optional<Long> userId) {

        User user;

        if(userId.isEmpty() || userId.get() < 1){
            user = null;
        }else{
            user = this.userRepository.findById(userId.get()).orElse(null);
        }

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<Product> listProduct = this.productRepository.findAllByNameProductLikeAndIdStatus("%"+name+"%", Status_Enum.EXISTS.getCode(), pageable).toList();

        List<ProductShowUserResponseDTO> listProductShowUserResponseDTOS;

        if(Objects.isNull(user)){
            listProductShowUserResponseDTOS = listProduct.stream().map(this.productMapping::toProductShowUser).collect(Collectors.toList());
        }else {
            listProductShowUserResponseDTOS = listProduct.stream().map(product -> this.productMapping.toProductShowUserAndLike(product, user)).collect(Collectors.toList());
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.SEARCH_PRODUCT_ON_USER_SUCCESS, listProductShowUserResponseDTOS);
    }

    @Override
    public ServiceResult<ProductShowUserResponseDTO> showProductOnUser(Optional<Long> idProduct) {

        if(idProduct.isEmpty() || idProduct.get() < 1)
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Product.Validate_Product_Request.VALIDATE_ID_PRODUCT, null);
        Product product = this.productRepository.findById(idProduct.get()).orElse(null);

        if(Objects.isNull(product))
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Product.Validate_Product_Request.VALIDATE_ID_PRODUCT, null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.SHOW_PRODUCT_IS_SUCCESS, this.productMapping.toProductShowUser(product));

    }

    @Override
    public ServiceResult<ProductFilterColorResponseDTO> findProductUserByColor(Optional<Long> idProduct, Optional<Long> idColor) {

        if(idProduct.isEmpty() || idProduct.get() < 1
            || idColor.isEmpty() || idColor.get() < 1)
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "", null);

        Product product = this.productRepository.findById(idProduct.get()).orElse(null);

        Color color = this.colorRepository.findById(idColor.get()).orElse(null);

        return new ServiceResult<>(HttpStatus.OK, "", this.productMapping.toProductFilterColorResponseDTO(product, color));
    }

    @Override
    public ServiceResult<ProductDetailUserDTO> findByIdProductAndIdColorAndIdSize(Optional<Long> idProduct, Optional<Integer> idColor, Optional<Integer> idSize) {

        if(idProduct.isEmpty()
            ||idProduct.get() < 1
            ||idColor.isEmpty()
            ||idColor.get() < 1
            ||idSize.isEmpty()
            ||idSize.get() < 1)
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,"", null);

        Product product = this.productRepository.findById(idProduct.get()).orElse(null);

        if(Objects.isNull(product))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, "", null);

        return new ServiceResult<>(HttpStatus.OK, "", this.productMapping.toProductDetailUserDTO(product, idColor.get(), idSize.get()));
    }

    @Override
    public ServiceResult<List<ProductShowUserResponseDTO>> getListNewProduct(Optional<Integer> limit, Optional<Long> userId) {

        User user;

        if(userId.isEmpty() || userId.get() < 1){
            user = null;
        }else{
            user = this.userRepository.findById(userId.get()).orElse(null);
        }

        if(limit.isEmpty()
                ||limit.get() < 1)
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,"", null);
        Pageable pageable = PageRequest.of(0, limit.get());
        List<Product> listProduct = this.productRepository.findAllProductNew(pageable).toList();

        List<ProductShowUserResponseDTO> listProductShowUserResponseDTOS;

        if(Objects.isNull(user)){
            listProductShowUserResponseDTOS = listProduct.stream().map(this.productMapping::toProductShowUser).collect(Collectors.toList());
        }else {
            listProductShowUserResponseDTOS = listProduct.stream().map(product -> this.productMapping.toProductShowUserAndLike(product, user)).collect(Collectors.toList());
        }
        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_ON_USER_SUCCESS, listProductShowUserResponseDTOS);
    }

    @Override
    public ServiceResult<List<ProductShowUserResponseDTO>> getListHotProduct(Optional<Integer> limit, Optional<Long> userId) {
        User user;

        if(userId.isEmpty() || userId.get() < 1){
            user = null;
        }else{
            user = this.userRepository.findById(userId.get()).orElse(null);
        }
        if(limit.isEmpty()
                ||limit.get() < 1)
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,"", null);
        Pageable pageable = PageRequest.of(0, limit.get());
        List<Product> listProduct = this.productRepository.findAllProductHot(pageable).toList();

        List<ProductShowUserResponseDTO> listProductShowUserResponseDTOS;

        if(Objects.isNull(user)){
            listProductShowUserResponseDTOS = listProduct.stream().map(this.productMapping::toProductShowUser).collect(Collectors.toList());
        }else {
            listProductShowUserResponseDTOS = listProduct.stream().map(product -> this.productMapping.toProductShowUserAndLike(product, user)).collect(Collectors.toList());
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_ON_USER_SUCCESS, listProductShowUserResponseDTOS);
    }

    @Override
    public ServiceResult<List<ProductShowUserResponseDTO>> getListProductByCategory(Optional<Integer> idCategory, Optional<Integer> page, Optional<Integer> limit, Optional<Long> userId) {

        User user;

        if(userId.isEmpty() || userId.get() < 1){
            user = null;
        }else{
            user = this.userRepository.findById(userId.get()).orElse(null);
        }

        if(idCategory.isEmpty()
                ||idCategory.get() < 1
                ||page.isEmpty()
                ||page.get() < 0
                ||limit.isEmpty()
                ||limit.get() < 1)
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,"", null);

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());

        List<Product> listProduct = this.productRepository.findAllByIdCategoryParentAndIdStatus(idCategory.get(), Status_Enum.EXISTS.getCode(), pageable).toList();

        List<ProductShowUserResponseDTO> listProductShowUserResponseDTOS;

        if(Objects.isNull(user)){
            listProductShowUserResponseDTOS = listProduct.stream().map(this.productMapping::toProductShowUser).collect(Collectors.toList());
        }else {
            listProductShowUserResponseDTOS = listProduct.stream().map(product -> this.productMapping.toProductShowUserAndLike(product, user)).collect(Collectors.toList());
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_ON_USER_SUCCESS, listProductShowUserResponseDTOS);
    }

    @Override
    public ServiceResult<List<ProductShowUserResponseDTO>> getListProductLikeByUser(Optional<Integer> idUser, Optional<Integer> page, Optional<Integer> limit) {

        User user;

        if(idUser.isEmpty() || idUser.get() < 1){
            user = null;
        }else{
            user = this.userRepository.findById(idUser.get().longValue()).orElse(null);
        }

        if(idUser.isEmpty()
                ||idUser.get() < 1
                ||page.isEmpty()
                ||page.get() < 0
                ||limit.isEmpty()
                ||limit.get() < 1)
            return new ServiceResult<>(HttpStatus.BAD_REQUEST,"", null);
        Pageable pageable = PageRequest.of(page.orElse(0), limit.get());
        List<Product> listProduct = this.productRepository.findProductLikeByIdUser(idUser.get(), pageable).toList();

        List<ProductShowUserResponseDTO> listProductShowUserResponseDTOS;

        if(Objects.isNull(user)){
            listProductShowUserResponseDTOS = listProduct.stream().map(this.productMapping::toProductShowUser).collect(Collectors.toList());
        }else {
            listProductShowUserResponseDTOS = listProduct.stream().map(product -> this.productMapping.toProductShowUserAndLike(product, user)).collect(Collectors.toList());
        }

        return new ServiceResult<>(HttpStatus.OK, Notification.Product.GET_LIST_PRODUCT_ON_USER_SUCCESS, listProductShowUserResponseDTOS);
    }

}
