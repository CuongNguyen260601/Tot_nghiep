package com.localbrand.repository;

import com.localbrand.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>, JpaSpecificationExecutor<ProductDetail> {

    List<ProductDetail> findAllByIdProduct(Integer idProduct);

    @Query(
            value = "select * from _Product_Detail pd " +
                    " join _Product p on p.idProduct = pd.idProduct " +
                    " join _Product_Sale ps on pd.idProductDetail = ps.idProductDetail " +
                    " where p.idProduct = :idProduct " +
                    "    and pd.idStatus = :idStatusPD " +
                    "    and ps.idStatus = :idStatusPS " +
                    "    and ps.dateEnd >= getdate()",
            nativeQuery = true
    )
    List<ProductDetail> findAllByIdProductSale(Integer idProduct, Integer idStatusPD, Integer idStatusPS);

    Page<ProductDetail> findAllByIdProduct(Integer idProduct, Pageable pageable);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in ( "+
            "select distinct pd.idProductDetail from ProductDetail pd " +
            " join ProductTag pt on pt.idProductDetail = pd.idProductDetail" +
            " where pt.idTag = :idTag " +
            " and pd.idProduct = :idProduct)"
    )
    Page<ProductDetail> findAllByIdTag(Integer idProduct, Integer idTag, Pageable pageable);

    Page<ProductDetail> findAllByIdProductAndIdSize(Integer idProduct, Integer idSize, Pageable pageable);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in ( "+
            "select distinct pd.idProductDetail from ProductDetail pd " +
                    " join ProductTag pt on pt.idProductDetail = pd.idProductDetail" +
                    " where pt.idTag = :idTag " +
                    " and pd.idProduct = :idProduct" +
                    " and pd.idSize = :idSize)"
    )
    Page<ProductDetail> findAllByIdTagAndIdSize(Integer idProduct, Integer idTag, Integer idSize, Pageable pageable);

    Page<ProductDetail> findAllByIdProductAndIdColor(Integer idProduct, Integer idColor, Pageable pageable);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in ( "+
            "select distinct pd.idProductDetail from ProductDetail pd " +
                    " join ProductTag pt on pt.idProductDetail = pd.idProductDetail" +
                    " where pt.idTag = :idTag " +
                    " and pd.idProduct = :idProduct" +
                    " and pd.idColor = :idColor)"
    )
    Page<ProductDetail> findAllByIdTagAndIdColor(Integer idProduct, Integer idTag, Integer idColor, Pageable pageable);

    Page<ProductDetail> findAllByIdProductAndIdSizeAndIdColor(Integer idProduct, Integer idSize, Integer idColor,Pageable pageable);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in ( "+
            "select distinct pd.idProductDetail from ProductDetail pd " +
                    " join ProductTag pt on pt.idProductDetail = pd.idProductDetail" +
                    " where pt.idTag = :idTag " +
                    " and pd.idProduct = :idProduct" +
                    " and pd.idSize = :idSize" +
                    " and pd.idColor = :idColor)"
    )
    Page<ProductDetail> findAllByIdTagAndIdSizeAndIdColor(Integer idProduct, Integer idTag, Integer idSize, Integer idColor, Pageable pageable);

    Page<ProductDetail> findAllByIdProductAndIdStatus(Integer idProduct, Integer idStatus, Pageable pageable);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in ( "+
            "select distinct pd.idProductDetail from ProductDetail pd " +
                    " join ProductTag pt on pt.idProductDetail = pd.idProductDetail" +
                    " where pt.idTag = :idTag " +
                    " and pd.idProduct = :idProduct" +
                    " and pd.idStatus = :idStatus)"
    )
    Page<ProductDetail> findAllByIdTagAndIdStatus(Integer idProduct, Integer idTag, Integer idStatus,Pageable pageable);

    Page<ProductDetail> findAllByIdProductAndIdSizeAndIdStatus(Integer idProduct, Integer idSize, Integer idStatus,Pageable pageable);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in ( "+
            "select distinct pd.idProductDetail from ProductDetail pd " +
                    " join ProductTag pt on pt.idProductDetail = pd.idProductDetail" +
                    " where pt.idTag = :idTag " +
                    " and pd.idProduct = :idProduct" +
                    " and pd.idSize = :idSize" +
                    " and pd.idStatus = :idStatus)"
    )
    Page<ProductDetail> findAllByIdTagAndIdSizeAndIdStatus(Integer idProduct, Integer idTag, Integer idSize, Integer idStatus, Pageable pageable);

    Page<ProductDetail> findAllByIdProductAndIdColorAndIdStatus(Integer idProduct, Integer idColor, Integer idStatus,Pageable pageable);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in ( "+
            "select distinct pd.idProductDetail from ProductDetail pd " +
                    " join ProductTag pt on pt.idProductDetail = pd.idProductDetail" +
                    " where pt.idTag = :idTag " +
                    " and pd.idProduct = :idProduct" +
                    " and pd.idColor = :idColor" +
                    " and pd.idStatus = :idStatus)"
    )
    Page<ProductDetail> findAllByIdTagAndIdColorAndIdStatus(Integer idProduct, Integer idTag, Integer idColor, Integer idStatus,Pageable pageable);

    Page<ProductDetail> findAllByIdProductAndIdSizeAndIdColorAndIdStatus(Integer idProduct, Integer idSize, Integer idColor, Integer idStatus,Pageable pageable);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in ( "+
            "select distinct pd.idProductDetail from ProductDetail pd " +
                    " join ProductTag pt on pt.idProductDetail = pd.idProductDetail" +
                    " where pt.idTag = :idTag " +
                    " and pd.idProduct = :idProduct" +
                    " and pd.idSize = :idSize" +
                    " and pd.idColor = :idColor" +
                    " and pd.idStatus = :idStatus)"
    )
    Page<ProductDetail> findAllByIdTagAndIdSizeAndIdColorAndIdStatus(Integer idProduct, Integer idTag, Integer idSize, Integer idColor, Integer idStatus, Pageable pageable);


    @Query(
            "select pd.detailPhoto from ProductDetail pd " +
                    " join Product p on p.idProduct = pd.idProduct " +
                    " where p.idProduct = :idProduct " +
                    " and pd.idColor = :idColor"
    )
    List<String> findImageByColorAndIdProduct(Long idProduct, Integer idColor);

    @Query(
            "select sum(pd.quantity) from ProductDetail pd " +
                    " where pd.idProductDetail in ( " +
                    " select distinct(pd.idProductDetail) from ProductDetail pd" +
                    " where pd.idProduct = :idProduct " +
                    " and pd.idColor = :idColor " +
                    " and pd.idStatus = 2)"
    )
    Integer countByIdProductAndIdColor(Integer idProduct, Integer idColor);

    @Query(
            "select min(pd.price) from ProductDetail pd " +
                    " where pd.idProduct = :idProduct " +
                    " and pd.idColor = :idColor " +
                    " and pd.idStatus = 2"
    )
    Float minPriceByIdProductAndIdColor(Integer idProduct, Integer idColor);

    @Query(
            "select max(pd.price) from ProductDetail pd " +
                    " where pd.idProduct = :idProduct " +
                    " and pd.idColor = :idColor " +
                    " and pd.idStatus = 2"
    )
    Float maxPriceByIdProductAndIdColor(Integer idProduct, Integer idColor);

    ProductDetail findByIdProductAndAndIdColorAndIdSizeAndIdStatus(Integer idProduct, Integer idColor, Integer idSize, Integer idStatus);

    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in :idProductDetail"
    )
    List<ProductDetail> findAllByListIdProductDetail(List<Long> idProductDetail);
    @Query(
            "select pd from ProductDetail pd " +
                    " where pd.idProductDetail in :idProductDetail" +
                    " order by pd.quantity asc"
    )
    List<ProductDetail> findAllByListIdProductDetailAndSort(List<Long> idProductDetail);

    @Query(
            "select pd from ProductDetail pd" +
                    " where pd.quantity = 0"
    )
    List<ProductDetail> findAllByQuantity();

    @Query(
            value = "select * from _Product_Detail pd "+
            " where pd.idProductDetail in ( "+
            " select distinct bp1.idProductDetail from _Bill_Product bp1 "+
            " join _Bill b on b.idBill = bp1.idBill "+
            " where bp1.idStatus = :idStatus "+
            " and month(b.dateSuccess) = month(current_timestamp) "+
            " group by bp1.idProductDetail "+
            " having count(bp1.idProductDetail) >= :a)",
            nativeQuery = true
    )
    List<ProductDetail> findAllProductDetailHot(Integer idStatus, Integer a);
}