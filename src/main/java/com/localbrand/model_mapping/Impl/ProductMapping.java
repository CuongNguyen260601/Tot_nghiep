package com.localbrand.model_mapping.Impl;

import com.localbrand.common.Status_Enum;
import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.GenderDTO;
import com.localbrand.dto.SizeDTO;
import com.localbrand.dto.request.ProductColorRequestDTO;
import com.localbrand.dto.request.ProductRequestDTO;
import com.localbrand.dto.request.ProductSizeRequestDTO;
import com.localbrand.dto.response.*;
import com.localbrand.entity.*;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapping implements Mapping<ProductRequestDTO, Product> {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    private final ColorRepository colorRepository;
    private final ColorMapping colorMapping;
    private final SizeRepository sizeRepository;
    private final SizeMapping sizeMapping;
    private final CategoryRepository categoryRepository;
    private final CategoryChildMapping categoryChildMapping;
    private final SaleRepository saleRepository;
    private final SaleMapping saleMapping;
    private final ProductTagRepository productTagRepository;
    private final TagRepository tagRepository;
    private final GenderRepository genderRepository;
    private final CategoryParentMapping categoryParentMapping;
    private final LikeRepository likeRepository;

    @Override
    public ProductRequestDTO toDto(Product product) {
        return null;
    }

    @Override
    public Product toEntity(ProductRequestDTO productRequestDTO) {

        return Product
                .builder()
                .idProduct(productRequestDTO.getIdProduct())
                .nameProduct(productRequestDTO.getNameProduct())
                .dateCreate(productRequestDTO.getDateCreate())
                .idStatus(productRequestDTO.getIdStatus())
                .descriptionProduct(productRequestDTO.getDescriptionProduct())
                .frontPhoto(productRequestDTO.getFrontPhoto())
                .backPhoto(productRequestDTO.getBackPhoto())
                .coverPhoto(productRequestDTO.getCoverPhoto())
                .build();
    }

    public List<ProductDetail> toListProductDetail(Product product, ProductRequestDTO productRequestDTO){

        List<ProductDetail> listProductDetail = new ArrayList<>();

        for(ProductColorRequestDTO colorRequestDTO
                : productRequestDTO.getDetailInProduct().getListDetailColorRequest()){

            for(ProductSizeRequestDTO sizeRequestDTO
                    :colorRequestDTO.getListSizeInColor()){


                ProductDetail productDetail = ProductDetail
                        .builder()
                        .idGender(productRequestDTO.getDetailInProduct().getIdGender())
                        .idCategory(productRequestDTO.getDetailInProduct().getIdCategory())
                        .idColor(colorRequestDTO.getIdColor())
                        .idSize(sizeRequestDTO.getIdSize().intValue())
                        .price(sizeRequestDTO.getPrice())
                        .quantity(sizeRequestDTO.getQuantity())
                        .dateCreate(productRequestDTO.getDateCreate())
                        .detailPhoto(colorRequestDTO.getDetailPhoto())
                        .idStatus(sizeRequestDTO.getIdStatus())
                        .build();

                if(Objects.nonNull(sizeRequestDTO.getIdProductDetail()))
                    productDetail.setIdProductDetail(sizeRequestDTO.getIdProductDetail());

                if(Objects.nonNull(product.getIdProduct()))
                    productDetail.setIdProduct(product.getIdProduct().intValue());

                listProductDetail.add(productDetail);

                Integer toTal = Objects.isNull(product.getTotalProduct()) ? 0:product.getTotalProduct();

                product.setTotalProduct(toTal+sizeRequestDTO.getQuantity());
            }
        }

        return listProductDetail;
    }

    public ProductResponseDTO toProductResponseDTO(Product product){

         List<ProductDetail> listProductDetails = this.productDetailRepository.findAllByIdProduct(product.getIdProduct().intValue());

        Set<Integer> idGender = new HashSet<>();
        Set<CategoryChildDTO> categoryChildDTOS = new HashSet<>();
        Set<ProductColorResponseDTO> productColorResponseDTOS = new HashSet<>();

        for(ProductDetail productDetail : listProductDetails){
            idGender.add(productDetail.getIdGender());

            Category category =this.categoryRepository.
                    findById(productDetail.getIdCategory().
                            longValue()).orElseThrow(()-> new RuntimeException(ErrorCodes.CATEGORY_IS_NULL));

            categoryChildDTOS.add(this.categoryChildMapping.toDto(category));

            Color color = this.colorRepository.findById(productDetail.getIdColor().longValue())
                    .orElseThrow(() -> new RuntimeException(ErrorCodes.COLOR_IS_NULL));

            boolean isDuplicate = false;

            Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue())
                    .orElseThrow(() -> new RuntimeException(ErrorCodes.SIZE_IS_NULL));

            Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

            List<Integer> listTag = this.productTagRepository.listTagByProduct(productDetail.getIdProductDetail().intValue());

            for(ProductColorResponseDTO productColorResponseDTO1: productColorResponseDTOS){

                if(productColorResponseDTO1.getColor().getIdColor().equals(productDetail.getIdColor().longValue())){

                    ProductSizeResponseDTO productSizeResponseDTO = ProductSizeResponseDTO
                            .builder()
                            .idProductDetail(productDetail.getIdProductDetail())
                            .size(this.sizeMapping.toDto(size))
                            .quantity(productDetail.getQuantity())
                            .idStatus(productDetail.getIdStatus())
                            .listTag(listTag)
                            .build();

                    if(Objects.nonNull(sale)){
                        productSizeResponseDTO.setSale(this.saleMapping.toDto(sale));
                    }

                    productColorResponseDTO1.getListSizeInColor().add(productSizeResponseDTO);

                    isDuplicate = true;

                    break;
                }
            }

            if(!isDuplicate){
                ProductColorResponseDTO productColorResponseDTO = ProductColorResponseDTO
                        .builder()
                        .color(this.colorMapping.toDto(color))
                        .detailPhoto(productDetail.getDetailPhoto())
                        .listSizeInColor(new ArrayList<>())
                        .build();

                ProductSizeResponseDTO productSizeResponseDTO = ProductSizeResponseDTO
                        .builder()
                        .idProductDetail(productDetail.getIdProductDetail())
                        .size(this.sizeMapping.toDto(size))
                        .quantity(productDetail.getQuantity())
                        .price(productDetail.getPrice())
                        .idStatus(productDetail.getIdStatus())
                        .dateCreate(productDetail.getDateCreate())
                        .listTag(listTag)
                        .build();

                if(Objects.nonNull(sale)){
                    productSizeResponseDTO.setSale(this.saleMapping.toDto(sale));
                }

                productColorResponseDTO.getListSizeInColor().add(productSizeResponseDTO);

                productColorResponseDTOS.add(productColorResponseDTO);

            }

        }

        List<ProductColorResponseDTO> productColorResponseDTOS1 = new ArrayList<>(productColorResponseDTOS);

        ProductDetailResponseDTO productDetailResponseDTO = ProductDetailResponseDTO
                .builder()
                .idGender(idGender.iterator().next())
                .category(categoryChildDTOS.iterator().next())
                .listDetailColorResponse(productColorResponseDTOS1)
                .build();

        return ProductResponseDTO
                .builder()
                .idProduct(product.getIdProduct())
                .nameProduct(product.getNameProduct())
                .dateCreate(product.getDateCreate())
                .totalProduct(product.getTotalProduct())
                .idStatus(product.getIdStatus())
                .descriptionProduct(product.getDescriptionProduct())
                .frontPhoto(product.getFrontPhoto())
                .backPhoto(product.getBackPhoto())
                .coverPhoto(product.getCoverPhoto())
                .detailInProduct(productDetailResponseDTO)
                .build();
    }

    public ProductParentResponseDTO toProductParent(Product product){

        Gender gender = this.genderRepository.findByIdProduct(product.getIdProduct());

        GenderDTO genderDTO = GenderDTO
                .builder()
                .idGender(gender.getIdGender())
                .nameGender(gender.getNameGender())
                .build();

        return ProductParentResponseDTO
                .builder()
                .idProduct(product.getIdProduct())
                .nameProduct(product.getNameProduct())
                .dateCreate(product.getDateCreate())
                .totalProduct(product.getTotalProduct())
                .idStatus(product.getIdStatus())
                .descriptionProduct(product.getDescriptionProduct())
                .coverPhoto(product.getCoverPhoto())
                .frontPhoto(product.getFrontPhoto())
                .backPhoto(product.getBackPhoto())
                .categoryParentDTO(this.categoryParentMapping
                        .toDto(this.categoryRepository
                                .findCategoryParentByIdProduct(product.getIdProduct()), new ArrayList<>()))
                .categoryChildDTO(this.categoryChildMapping.toDto(this.categoryRepository
                        .findCategoryChildByIdProduct(product.getIdProduct())))
                .genderDTO(genderDTO)
                .build();
    }

    public ProductChildResponseDTO toProductChild(ProductDetail productDetail){

        Category category = this.categoryRepository
                .findById(productDetail.getIdCategory().longValue()).orElse(null);

        Color color = this.colorRepository
                .findById(productDetail.getIdColor().longValue()).orElse(null);

        Size size = this.sizeRepository
                .findById(productDetail.getIdSize().longValue()).orElse(null);

        Sale sale = this.saleRepository
                .findSaleByProductDetail(productDetail.getIdProductDetail());

        List<Integer> listTag= this.productTagRepository
                .listTagByProduct(productDetail.getIdProductDetail().intValue());

        Product product = this.productRepository.findById(productDetail.getIdProduct().longValue()).orElse(null);

        ProductChildResponseDTO productChildResponseDTO = ProductChildResponseDTO
                .builder()
                .idProductDetail(productDetail.getIdProductDetail())
                .nameProduct(product.getNameProduct())
                .idProduct(productDetail.getIdProduct())
                .idGender(productDetail.getIdGender())
                .category(this.categoryChildMapping.toDto(category))
                .color(this.colorMapping.toDto(color))
                .size(this.sizeMapping.toDto(size))
                .price(productDetail.getPrice())
                .quantity(productDetail.getQuantity())
                .dateCreate(productDetail.getDateCreate())
                .detailPhoto(productDetail.getDetailPhoto())
                .idStatus(productDetail.getIdStatus())
                .listTag(listTag)
                .build();

        if(!Objects.isNull(sale))
            productChildResponseDTO.setSaleDTO(this.saleMapping.toDto(sale));
        return productChildResponseDTO;
    }

    public ProductShowUserResponseDTO toProductShowUser(Product product){

        List<Color> listColor = this.colorRepository.findAllByIdProduct(product.getIdProduct());

        ProductShowUserResponseDTO productShowUserResponseDTO = new ProductShowUserResponseDTO();

        Integer totalLike = likeRepository.countLikeByIdProduct(product.getIdProduct().intValue());

        productShowUserResponseDTO.setIdProduct(product.getIdProduct());
        productShowUserResponseDTO.setNameProduct(product.getNameProduct());
        productShowUserResponseDTO.setDateCreate(product.getDateCreate());
        productShowUserResponseDTO.setTotalProduct(product.getTotalProduct());
        productShowUserResponseDTO.setIdStatus(product.getIdStatus());
        productShowUserResponseDTO.setDescriptionProduct(product.getDescriptionProduct());
        productShowUserResponseDTO.setCoverPhoto(product.getCoverPhoto());
        productShowUserResponseDTO.setFrontPhoto(product.getFrontPhoto());
        productShowUserResponseDTO.setBackPhoto(product.getBackPhoto());
        productShowUserResponseDTO.setListTag(this.tagRepository.findByIdProduct(product.getIdProduct()));
        productShowUserResponseDTO.setMinPrice(this.productRepository.minPrice(product.getIdProduct()));
        productShowUserResponseDTO.setMaxPrice(this.productRepository.maxPrice(product.getIdProduct()));
        productShowUserResponseDTO.setListColor(listColor.stream().map(this.colorMapping::toDto).collect(Collectors.toList()));
        productShowUserResponseDTO.setLike(totalLike);
        productShowUserResponseDTO.setCategoryChildDTO(this.categoryChildMapping.toDto(this.categoryRepository.findCategoryChildByIdProduct(product.getIdProduct())));
        productShowUserResponseDTO.setCategoryParentDTO(this.categoryParentMapping.toDto(this.categoryRepository.findCategoryParentByIdProduct(product.getIdProduct())));
        productShowUserResponseDTO.setIsLike(false);
        List<ProductDetail> productDetails = this.productDetailRepository.findAllByIdProductSale(product.getIdProduct().intValue(), Status_Enum.EXISTS.getCode(), Status_Enum.EXISTS.getCode());

        List<ProductChildResponseDTO> productChildResponseDTOS = new ArrayList<>();

        for (ProductDetail productDetail : productDetails) {
            ProductChildResponseDTO productChildResponseDTO = this.toProductChild(productDetail);
            productChildResponseDTOS.add(productChildResponseDTO);
        }
        productShowUserResponseDTO.setListProductSale(productChildResponseDTOS);

        Gender gender = this.genderRepository.findByIdProduct(product.getIdProduct());
        GenderDTO genderDTO = GenderDTO.builder()
                .idGender(gender.getIdGender())
                .nameGender(gender.getNameGender())
                .build();

        productShowUserResponseDTO.setGenderDTO(genderDTO);
        return productShowUserResponseDTO;
    }

    public ProductFilterColorResponseDTO toProductFilterColorResponseDTO(Product product, Color color){

        ProductFilterColorResponseDTO productFilterColorResponseDTO = new ProductFilterColorResponseDTO();

        Integer totalLike = likeRepository.countLikeByIdProduct(product.getIdProduct().intValue());

        productFilterColorResponseDTO.setIdProduct(product.getIdProduct());
        productFilterColorResponseDTO.setNameProduct(product.getNameProduct());
        productFilterColorResponseDTO.setDateCreate(product.getDateCreate());
        productFilterColorResponseDTO.setTotalProduct(product.getTotalProduct());
        productFilterColorResponseDTO.setIdStatus(product.getIdStatus());
        productFilterColorResponseDTO.setDescriptionProduct(product.getDescriptionProduct());
        productFilterColorResponseDTO.setCoverPhoto(product.getCoverPhoto());
        productFilterColorResponseDTO.setFrontPhoto(product.getFrontPhoto());
        productFilterColorResponseDTO.setBackPhoto(product.getBackPhoto());
        productFilterColorResponseDTO.setColorDTO(this.colorMapping.toDto(color));
        productFilterColorResponseDTO.setPhotoByColor(this.productDetailRepository.findImageByColorAndIdProduct(product.getIdProduct(), color.getIdColor().intValue()).get(0));

        productFilterColorResponseDTO.setAmount(this.productDetailRepository
                .countByIdProductAndIdColor(product.getIdProduct().intValue(), color.getIdColor().intValue()));
        productFilterColorResponseDTO.setMinPrice(this.productDetailRepository.minPriceByIdProductAndIdColor(product.getIdProduct().intValue(), color.getIdColor().intValue()));
        productFilterColorResponseDTO.setMaxPrice(this.productDetailRepository.maxPriceByIdProductAndIdColor(product.getIdProduct().intValue(), color.getIdColor().intValue()));

        List<Size> listSize = this.sizeRepository
                .findAllByIdProductAndIdColor(product.getIdProduct(), color.getIdColor().intValue());

        List<SizeAndTagInProductShowUser> listSizeAndTagInProductShowUsers = new ArrayList<>();

        if(!listSize.isEmpty()){

            for (Size size: listSize) {
                SizeDTO sizeDTO = this.sizeMapping.toDto(size);

                List<Integer> listTag = this.tagRepository.findByIdProductAndIdColorAndIdSize(product.getIdProduct(), color.getIdColor().intValue(), size.getIdSize().intValue());

                listSizeAndTagInProductShowUsers.add(new SizeAndTagInProductShowUser(sizeDTO, listTag));
            }

            productFilterColorResponseDTO.setSizeAndTag(listSizeAndTagInProductShowUsers);
        }

        productFilterColorResponseDTO.setLike(totalLike);
        return productFilterColorResponseDTO;
    }

    public ProductResponseShowAdminDTO toProductResponseShowDTO(Product product){

        List<ProductDetail> listProductDetails
                = this.productDetailRepository
                .findAllByIdProduct(product.getIdProduct().intValue());

        Set<Integer> idGender = new HashSet<>();
        Set<Integer> idCategory = new HashSet<>();
        Set<ProductColorResponseShowDTO> productColorResponseShowDTOS = new HashSet<>();

        for(ProductDetail productDetail : listProductDetails){
            idGender.add(productDetail.getIdGender());

            idCategory.add(productDetail.getIdCategory());

            boolean isDuplicate = false;

            for(ProductColorResponseShowDTO productColorResponseShowDTO: productColorResponseShowDTOS){

                    if(productColorResponseShowDTO.getIdColor() == (productDetail.getIdColor().longValue())){

                        Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

                        ProductSizeResponseShowDTO productSizeResponseShowDTO = ProductSizeResponseShowDTO
                                .builder()
                                .idProductDetail(productDetail.getIdProductDetail())
                                .idSize(productDetail.getIdSize().longValue())
                                .quantity(productDetail.getQuantity())
                                .price(productDetail.getPrice())
                                .idStatus(productDetail.getIdStatus())
                                .dateCreate(productDetail.getDateCreate())
                                .build();

                        if(Objects.nonNull(sale)){
                            productSizeResponseShowDTO.setIdSale(sale.getIdSale().intValue());
                        }else{
                            productSizeResponseShowDTO.setIdSale(null);
                        }
                        productColorResponseShowDTO.getListSizeInColor().add(productSizeResponseShowDTO);

                        isDuplicate = true;
                        break;
                    }
                }

            if(!isDuplicate){
                ProductColorResponseShowDTO productColorResponseShowDTO = ProductColorResponseShowDTO
                        .builder()
                        .idColor(productDetail.getIdColor())
                        .detailPhoto(productDetail.getDetailPhoto())
                        .listSizeInColor(new ArrayList<>())
                        .build();

                Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue())
                        .orElseThrow(() -> new RuntimeException(ErrorCodes.SIZE_IS_NULL));

                Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

                ProductSizeResponseShowDTO productSizeResponseShowDTO = ProductSizeResponseShowDTO
                        .builder()
                        .idProductDetail(productDetail.getIdProductDetail())
                        .idSize(size.getIdSize())
                        .quantity(productDetail.getQuantity())
                        .price(productDetail.getPrice())
                        .idStatus(productDetail.getIdStatus())
                        .dateCreate(productDetail.getDateCreate())
                        .build();

                if(Objects.nonNull(sale))
                    productSizeResponseShowDTO.setIdSale(sale.getIdSale().intValue());

                productColorResponseShowDTO.getListSizeInColor().add(productSizeResponseShowDTO);

                productColorResponseShowDTOS.add(productColorResponseShowDTO);
            }
        }

        List<ProductColorResponseShowDTO> productColorResponseShowDTOS1 = new ArrayList<>(productColorResponseShowDTOS);

        ProductDetailResponseShowDTO productDetailResponseShowDTO = ProductDetailResponseShowDTO
                .builder()
                .idGender(idGender.iterator().next())
                .idCategory(idCategory.iterator().next())
                .listDetailColorRequest(productColorResponseShowDTOS1)
                .build();

        ProductResponseShowDTO productResponseShowDTO = ProductResponseShowDTO
                .builder()
                .idProduct(product.getIdProduct())
                .nameProduct(product.getNameProduct())
                .dateCreate(product.getDateCreate())
                .totalProduct(product.getTotalProduct())
                .idStatus(product.getIdStatus())
                .descriptionProduct(product.getDescriptionProduct())
                .frontPhoto(product.getFrontPhoto())
                .backPhoto(product.getBackPhoto())
                .coverPhoto(product.getCoverPhoto())
                .detailInProduct(productDetailResponseShowDTO)
                .build();

        Category category = this.categoryRepository.findById(productResponseShowDTO.getDetailInProduct().getIdCategory().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.CATEGORY_IS_NULL));

        return ProductResponseShowAdminDTO
                .builder()
                .idCategoryParent(category.getParentId())
                .productResponseShowDTO(productResponseShowDTO)
                .build();
    }

    public ProductDetailUserDTO toProductDetailUserDTO(Product product, Integer idColor, Integer idSize){

        ProductDetailUserDTO productDetailUserDTO = new ProductDetailUserDTO();

        Integer totalLike = likeRepository.countLikeByIdProduct(product.getIdProduct().intValue());

        productDetailUserDTO.setIdProduct(product.getIdProduct());
        productDetailUserDTO.setNameProduct(product.getNameProduct());
        productDetailUserDTO.setDateCreate(product.getDateCreate());
        productDetailUserDTO.setTotalProduct(product.getTotalProduct());
        productDetailUserDTO.setIdStatus(product.getIdStatus());
        productDetailUserDTO.setDescriptionProduct(product.getDescriptionProduct());
        productDetailUserDTO.setCoverPhoto(product.getCoverPhoto());
        productDetailUserDTO.setFrontPhoto(product.getFrontPhoto());
        productDetailUserDTO.setBackPhoto(product.getBackPhoto());


        ProductDetail productDetail = this.productDetailRepository
                .findByIdProductAndAndIdColorAndIdSizeAndIdStatus(
                        product.getIdProduct().intValue(),
                        idColor,
                        idSize,
                        Status_Enum.EXISTS.getCode());



        if(Objects.nonNull(productDetail)){
            Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

            Category category = this.categoryRepository.findById(productDetail.getIdCategory().longValue())
                    .orElseThrow(() -> new RuntimeException(ErrorCodes.CATEGORY_IS_NULL));

            Color color = this.colorRepository.findById(productDetail.getIdColor().longValue())
                    .orElseThrow(() -> new RuntimeException(ErrorCodes.COLOR_IS_NULL));

            Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue())
                    .orElseThrow(() -> new RuntimeException(ErrorCodes.SIZE_IS_NULL));

            productDetailUserDTO.setCategoryDTO(this.categoryChildMapping.toDto(category));
            productDetailUserDTO.setIdProductDetail(productDetail.getIdProductDetail());
            productDetailUserDTO.setIdGender(productDetail.getIdGender());
            productDetailUserDTO.setSizeDTO(this.sizeMapping.toDto(size));
            productDetailUserDTO.setPrice(productDetail.getPrice());
            productDetailUserDTO.setAmount(productDetail.getQuantity());
            productDetailUserDTO.setDateCreate(productDetail.getDateCreate());
            productDetailUserDTO.setDetailPhoto(productDetail.getDetailPhoto());
            productDetailUserDTO.setColorDTO(this.colorMapping.toDto(color));
            if(Objects.nonNull(sale))
                productDetailUserDTO.setSaleDTO(this.saleMapping.toDto(sale));

            List<Integer> listTag = this.tagRepository.findByIdProductDetail(productDetail.getIdProductDetail());

            if(!listTag.isEmpty()){
                productDetailUserDTO.setListTag(listTag);
            }
        }

        productDetailUserDTO.setLike(totalLike);

        return productDetailUserDTO;
    }

    public ProductDetailUserDTO toProductDetailUserDTOByProductDetail(ProductDetail productDetail){

        Product product = this.productRepository.findById(productDetail.getIdProduct().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.PRODUCT_IS_NULL));


        Integer totalLike = likeRepository.countLikeByIdProduct(product.getIdProduct().intValue());

        Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

        Category category = this.categoryRepository.findById(productDetail.getIdCategory().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.CATEGORY_IS_NULL));

        Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.SIZE_IS_NULL));

        Color color = this.colorRepository.findById(productDetail.getIdColor().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.COLOR_IS_NULL));

        List<Integer> listTag = this.tagRepository.findByIdProductDetail(productDetail.getIdProductDetail());


        ProductDetailUserDTO productDetailUserDTO = new ProductDetailUserDTO();

        productDetailUserDTO.setIdProduct(product.getIdProduct());
        productDetailUserDTO.setNameProduct(product.getNameProduct());
        productDetailUserDTO.setDateCreate(product.getDateCreate());
        productDetailUserDTO.setTotalProduct(product.getTotalProduct());
        productDetailUserDTO.setIdStatus(product.getIdStatus());
        productDetailUserDTO.setDescriptionProduct(product.getDescriptionProduct());
        productDetailUserDTO.setCoverPhoto(product.getCoverPhoto());
        productDetailUserDTO.setFrontPhoto(product.getFrontPhoto());
        productDetailUserDTO.setBackPhoto(product.getBackPhoto());
        productDetailUserDTO.setIdProductDetail(productDetail.getIdProductDetail());
        productDetailUserDTO.setIdGender(productDetail.getIdGender());
        productDetailUserDTO.setListTag(listTag);
        productDetailUserDTO.setColorDTO(this.colorMapping.toDto(color));
        productDetailUserDTO.setLike(totalLike);
        productDetailUserDTO.setPrice(productDetail.getPrice());
        productDetailUserDTO.setAmount(productDetail.getQuantity());
        productDetailUserDTO.setDateCreate(productDetail.getDateCreate());
        productDetailUserDTO.setDetailPhoto(productDetail.getDetailPhoto());
        productDetailUserDTO.setCategoryDTO(this.categoryChildMapping.toDto(category));
        productDetailUserDTO.setSizeDTO(this.sizeMapping.toDto(size));
        if(Objects.nonNull(sale))
            productDetailUserDTO.setSaleDTO(this.saleMapping.toDto(sale));

        return productDetailUserDTO;

    }

    public ProductShowUserResponseDTO toProductShowUserAndLike(Product product, User user){

        List<Color> listColor = this.colorRepository.findAllByIdProduct(product.getIdProduct());

        Integer totalLike = likeRepository.countLikeByIdProduct(product.getIdProduct().intValue());

        Like like = this.likeRepository.findFirstByIdUserAndIdProduct(user.getIdUser().intValue(), product.getIdProduct().intValue()).orElse(null);

        ProductShowUserResponseDTO productShowUserResponseDTO = new ProductShowUserResponseDTO();
        productShowUserResponseDTO.setIdProduct(product.getIdProduct());
        productShowUserResponseDTO.setNameProduct(product.getNameProduct());
        productShowUserResponseDTO.setDateCreate(product.getDateCreate());
        productShowUserResponseDTO.setTotalProduct(product.getTotalProduct());
        productShowUserResponseDTO.setIdStatus(product.getIdStatus());
        productShowUserResponseDTO.setDescriptionProduct(product.getDescriptionProduct());
        productShowUserResponseDTO.setCoverPhoto(product.getCoverPhoto());
        productShowUserResponseDTO.setFrontPhoto(product.getFrontPhoto());
        productShowUserResponseDTO.setBackPhoto(product.getBackPhoto());
        productShowUserResponseDTO.setListTag(this.tagRepository.findByIdProduct(product.getIdProduct()));
        productShowUserResponseDTO.setMinPrice(this.productRepository.minPrice(product.getIdProduct()));
        productShowUserResponseDTO.setMaxPrice(this.productRepository.maxPrice(product.getIdProduct()));
        productShowUserResponseDTO.setListColor(listColor.stream().map(this.colorMapping::toDto).collect(Collectors.toList()));
        productShowUserResponseDTO.setLike(totalLike);
        productShowUserResponseDTO.setCategoryChildDTO(this.categoryChildMapping.toDto(this.categoryRepository.findCategoryChildByIdProduct(product.getIdProduct())));
        productShowUserResponseDTO.setCategoryParentDTO(this.categoryParentMapping.toDto(this.categoryRepository.findCategoryParentByIdProduct(product.getIdProduct())));
        productShowUserResponseDTO.setIsLike(!Objects.isNull(like)&&like.getLikeProduct());
        Gender gender = this.genderRepository.findByIdProduct(product.getIdProduct());
        GenderDTO genderDTO = GenderDTO.builder()
                .idGender(gender.getIdGender())
                .nameGender(gender.getNameGender())
                .build();

        productShowUserResponseDTO.setGenderDTO(genderDTO);
        return productShowUserResponseDTO;
    }
}
