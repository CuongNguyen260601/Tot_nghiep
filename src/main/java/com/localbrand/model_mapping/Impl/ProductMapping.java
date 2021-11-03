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
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.*;
import com.localbrand.utils.TPF_Utils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductMapping implements Mapping<ProductRequestDTO, Product> {

    private final TPF_Utils tpf_utils;
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

    public ProductMapping(TPF_Utils tpf_utils,
                          ProductRepository productRepository,
                          ProductDetailRepository productDetailRepository,
                          ColorRepository colorRepository,
                          ColorMapping colorMapping,
                          SizeRepository sizeRepository,
                          SizeMapping sizeMapping,
                          CategoryRepository categoryRepository,
                          CategoryChildMapping categoryChildMapping,
                          SaleRepository saleRepository,
                          SaleMapping saleMapping,
                          ProductTagRepository productTagRepository,
                          TagRepository tagRepository,
                          GenderRepository genderRepository,
                          CategoryParentMapping categoryParentMapping,
                          LikeRepository likeRepository){
        this.tpf_utils = tpf_utils;
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
        this.colorRepository = colorRepository;
        this.colorMapping = colorMapping;
        this.sizeRepository = sizeRepository;
        this.sizeMapping = sizeMapping;
        this.categoryRepository = categoryRepository;
        this.categoryChildMapping = categoryChildMapping;
        this.saleRepository = saleRepository;
        this.saleMapping = saleMapping;
        this.productTagRepository = productTagRepository;
        this.tagRepository = tagRepository;
        this.genderRepository = genderRepository;
        this.categoryParentMapping = categoryParentMapping;
        this.likeRepository = likeRepository;
    }

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

                if(!Objects.isNull(sizeRequestDTO.getIdProductDetail()))
                    productDetail.setIdProductDetail(sizeRequestDTO.getIdProductDetail());

                if(!Objects.isNull(product.getIdProduct()))
                    productDetail.setIdProduct(product.getIdProduct().intValue());

                listProductDetail.add(productDetail);

                Integer toTal = Objects.isNull(product.getTotalProduct()) ? 0:product.getTotalProduct();

                product.setTotalProduct(toTal+sizeRequestDTO.getQuantity());
            }
        }

        return listProductDetail;
    }

    public ProductResponseDTO toProductResponseDTO(Product product){

         List<ProductDetail> listProductDetails
                 = this.productDetailRepository
                 .findAllByIdProduct(product.getIdProduct().intValue());

        Set<Integer> idGender = new HashSet<>();
        Set<CategoryChildDTO> categoryChildDTOS = new HashSet<>();
        Set<ProductColorResponseDTO> productColorResponseDTOS = new HashSet<>();

        for(ProductDetail productDetail : listProductDetails){
            idGender.add(productDetail.getIdGender());

            Category category =this.categoryRepository.
                    findById(productDetail.getIdCategory().
                            longValue()).orElse(null);
            if(!Objects.isNull(category))
                categoryChildDTOS.add(
                        this.categoryChildMapping
                                .toDto(category)
                );

            Color color = this.colorRepository.findById(productDetail.getIdColor().longValue()).orElse(null);

            if(!Objects.isNull(color)){

                boolean isDuplicate = false;

                for(ProductColorResponseDTO productColorResponseDTO1: productColorResponseDTOS){

                    if(productColorResponseDTO1.getColor().getIdColor().equals(productDetail.getIdColor().longValue())){

                        Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue()).orElse(null);

                        if(!Objects.isNull(size)){

                            Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

                            if(!Objects.isNull(sale)){

                                List<Integer> listTag = this.productTagRepository.listTagByProduct(productDetail.getIdProductDetail().intValue());

                                ProductSizeResponseDTO productSizeResponseDTO = ProductSizeResponseDTO
                                        .builder()
                                        .idProductDetail(productDetail.getIdProductDetail())
                                        .size(this.sizeMapping.toDto(size))
                                        .quantity(productDetail.getQuantity())
                                        .sale(this.saleMapping.toDto(sale))
                                        .idStatus(productDetail.getIdStatus())
                                        .listTag(listTag)
                                        .build();

                                productColorResponseDTO1.getListSizeInColor().add(productSizeResponseDTO);
                            }
                        }

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

                    Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue()).orElse(null);

                    if(!Objects.isNull(size)){

                        Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

                        List<Integer> listTag = this.productTagRepository.listTagByProduct(productDetail.getIdProductDetail().intValue());

                        ProductSizeResponseDTO productSizeResponseDTO = ProductSizeResponseDTO
                                    .builder()
                                    .idProductDetail(productDetail.getIdProductDetail())
                                    .size(this.sizeMapping.toDto(size))
                                    .quantity(productDetail.getQuantity())
                                    .price(productDetail.getPrice())
                                    .idStatus(productDetail.getIdStatus())
                                    .dateCreate(productDetail.getDateCreate())
                                    .build();

                        if(listTag.size()>0)
                            productSizeResponseDTO.setListTag(listTag);

                        if(!Objects.isNull(sale)){
                            productSizeResponseDTO.setSale(this.saleMapping.toDto(sale));
                        }

                        productColorResponseDTO.getListSizeInColor().add(productSizeResponseDTO);

                        productColorResponseDTOS.add(productColorResponseDTO);
                    }

                }

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
        ProductChildResponseDTO productChildResponseDTO = ProductChildResponseDTO
                .builder()
                .idProductDetail(productDetail.getIdProductDetail())
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

        if(listSize.size() > 0){

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

                            if(!Objects.isNull(sale)){

                                ProductSizeResponseShowDTO productSizeResponseShowDTO = ProductSizeResponseShowDTO
                                        .builder()
                                        .idProductDetail(productDetail.getIdProductDetail())
                                        .idSize(productDetail.getIdSize().longValue())
                                        .quantity(productDetail.getQuantity())
                                        .idSale(sale.getIdSale().intValue())
                                        .price(productDetail.getPrice())
                                        .idStatus(productDetail.getIdStatus())
                                        .dateCreate(productDetail.getDateCreate())
                                        .build();

                                productColorResponseShowDTO.getListSizeInColor().add(productSizeResponseShowDTO);
                            }else{
                                ProductSizeResponseShowDTO productSizeResponseShowDTO = ProductSizeResponseShowDTO
                                        .builder()
                                        .idProductDetail(productDetail.getIdProductDetail())
                                        .idSize(productDetail.getIdSize().longValue())
                                        .quantity(productDetail.getQuantity())
                                        .idSale(null)
                                        .price(productDetail.getPrice())
                                        .idStatus(productDetail.getIdStatus())
                                        .dateCreate(productDetail.getDateCreate())
                                        .build();

                                productColorResponseShowDTO.getListSizeInColor().add(productSizeResponseShowDTO);
                            }
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

                    Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue()).orElse(null);

                    if(!Objects.isNull(size)){

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

                        if(!Objects.isNull(sale))
                            productSizeResponseShowDTO.setIdSale(sale.getIdSale().intValue());

                        productColorResponseShowDTO.getListSizeInColor().add(productSizeResponseShowDTO);

                        productColorResponseShowDTOS.add(productColorResponseShowDTO);
                    }

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

        Category category = this.categoryRepository.findById(productResponseShowDTO.getDetailInProduct().getIdCategory().longValue()).orElse(null);
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



        if(!Objects.isNull(productDetail)){
            Sale sale = this.saleRepository.findSaleByProductDetail(productDetail.getIdProductDetail());

            if(Objects.nonNull(sale))
                productDetailUserDTO.setSaleDTO(this.saleMapping.toDto(sale));

            Category category = this.categoryRepository.findById(productDetail.getIdCategory().longValue()).orElse(null);

            if(!Objects.isNull(category))
                productDetailUserDTO.setCategoryDTO(this.categoryChildMapping.toDto(category));

            productDetailUserDTO.setIdProductDetail(productDetail.getIdProductDetail());
            productDetailUserDTO.setIdGender(productDetail.getIdGender());

            Size size = this.sizeRepository.findById(productDetail.getIdSize().longValue()).orElse(null);

            if(!Objects.isNull(size))
                productDetailUserDTO.setSizeDTO(this.sizeMapping.toDto(size));

            productDetailUserDTO.setPrice(productDetail.getPrice());
            productDetailUserDTO.setQuantity(productDetail.getQuantity());
            productDetailUserDTO.setDateCreate(productDetail.getDateCreate());
            productDetailUserDTO.setDetailPhoto(productDetail.getDetailPhoto());

        }

        List<Integer> listTag = this.tagRepository.findByIdProductDetail(productDetail.getIdProductDetail());

        productDetailUserDTO.setListTag(listTag);

        productDetailUserDTO.setLike(totalLike);
        return productDetailUserDTO;
    }
}
