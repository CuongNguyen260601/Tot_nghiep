package com.localbrand.repository;

import com.localbrand.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(
            "select p from Product p " +
                    " where p.idProduct in (" +
                    " select distinct (p1.idProduct) from Product p1 "+
                    " join ProductDetail pd on p1.idProduct = pd.idProduct " +
                    "  where pd.idGender = :idGender " +
                    ")"
    )
    Page<Product> findAllByIdGender(Integer idGender, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in ( "+
            "select distinct(p1.idProduct) from Product p1 " +
            " join ProductDetail pd on pd.idProduct = p1.idProduct " +
            " where pd.idCategory = :idCategoryChild )"
    )
    Page<Product> findAllByIdCategoryChild(Integer idCategoryChild, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on pd.idProduct = p1.idProduct " +
                    " where pd.idCategory = :idCategoryChild " +
                    " and pd.idGender = :idGender)"
    )
    Page<Product> findAllByIdCategoryChildAndIdGender(Integer idCategoryChild, Integer idGender, Pageable pageable);


    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on pd.idProduct = p1.idProduct " +
                    " join Category c on c.idCategory = pd.idCategory " +
                    " where c.parentId = :idCategoryParent)"
    )
    Page<Product> findAllByIdCategoryParent(Integer idCategoryParent, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on pd.idProduct = p1.idProduct " +
                    " join Category c on c.idCategory = pd.idCategory " +
                    " where pd.idGender = :idGender and c.parentId = :idCategoryParent)"
    )
    Page<Product> findAllByIdCategoryParentAndIdGender(Integer idCategoryParent, Integer idGender, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in ( " +
                    " select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on pd.idProduct = p1.idProduct " +
                    " where p1.idStatus = :idStatus " +
                    " and pd.price between :minPrice and :maxPrice" +
                    " )"
    )
    Page<Product> findAllByIdStatus(Integer idStatus, Float minPrice, Float maxPrice,Pageable pageable);

    Page<Product> findAllByIdStatus(Integer idStatus, Pageable pageable);

    @Query(
            value = "select * from _Product p where p.idProduct in ( " +
                    "    select distinct (p.idProduct) from _Product p " +
                    "                      join _Product_Detail pd on p.idProduct = pd.idProduct " +
                    "                      join _Product_Sale ps on ps.idProductDetail = ps.idProductDetail " +
                    "    where p.idStatus = :idStatusP " +
                    "      and pd.idStatus = :idStatusPd " +
                    "      and ps.idStatus = :idStatusPs " +
                    "      and ps.dateEnd >= (getdate()) " +
                    "    )",
            nativeQuery = true
    )
    Page<Product> findAllByIsSale(Integer idStatusP, Integer idStatusPd, Integer idStatusPs, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on p.idProduct = pd.idProduct " +
                    " where p1.idStatus = :idStatus " +
                    " and pd.idGender = :idGender)"
    )
    Page<Product> findAllByIdStatusAndIdGender(Integer idStatus, Integer idGender,Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on pd.idProduct = p1.idProduct " +
                    " where pd.idCategory = :idCategoryChild " +
                    " and p1.idStatus = :idStatus)"
    )
    Page<Product> findAllByIdCategoryChildAndIdStatus(Integer idCategoryChild, Integer idStatus, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on pd.idProduct = p1.idProduct " +
                    " where p1.idStatus = :idStatus and " +
                    " pd.idCategory = :idCategoryChild " +
                    " and pd.idGender = :idGender )"
    )
    Page<Product> findAllByIdCategoryChildAndIdGenderAndIdStatus(Integer idCategoryChild, Integer idGender, Integer idStatus, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on pd.idProduct = p1.idProduct " +
                    " join Category c on c.idCategory = pd.idCategory " +
                    " where p1.idStatus = :idStatus and c.parentId = :idCategoryParent)"
    )
    Page<Product> findAllByIdCategoryParentAndIdStatus(Integer idCategoryParent, Integer idStatus, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on pd.idProduct = p1.idProduct " +
                    " join Category c on c.idCategory = pd.idCategory " +
                    " where p1.idStatus = :idStatus and pd.idGender = :idGender and c.parentId = :idCategoryParent)"
    )
    Page<Product> findAllByIdCategoryParentAndIdGenderAndIdStatus(Integer idCategoryParent, Integer idGender, Integer idStatus, Pageable pageable);

    Page<Product> findAllByNameProductLike(String name, Pageable pageable);

    @Query(
            "select min(pd.price) from ProductDetail pd " +
                    " join Product  p on p.idProduct = pd.idProduct " +
                    " where p.idProduct = :idProduct"
    )
    Float minPrice(Long idProduct);

    @Query(
            "select max(pd.price) from ProductDetail pd " +
                    " join Product  p on p.idProduct = pd.idProduct " +
                    " where p.idProduct = :idProduct"
    )
    Float maxPrice(Long idProduct);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on p1.idProduct = pd.idProduct " +
                    " where pd.idGender = :idGender " +
                    " and pd.price between :minPrice and :maxPrice" +
                    " and pd.idStatus = 2 and p1.idStatus = :idStatus)"
    )
    Page<Product> findAllByIdGenderAndIdStatus(Integer idGender, Integer idStatus, Float minPrice, Float maxPrice,Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on p1.idProduct = pd.idProduct " +
                    " where p1.idStatus = :idStatus " +
                    " and pd.idCategory = :idCategoryChild " +
                    " and pd.price between :minPrice and :maxPrice)"
    )
    Page<Product> findAllByIdCategoryAndIdStatus(Integer idCategoryChild, Integer idStatus, Float minPrice, Float maxPrice, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on p1.idProduct = pd.idProduct " +
                    " where p1.idStatus = :idStatus " +
                    " and pd.idCategory = :idCategoryChild " +
                    " and pd.idGender = :idGender" +
                    " and pd.price between :minPrice and :maxPrice)"
    )
    Page<Product> findAllByIdCategoryAndIdStatusAndIdGender(Integer idCategoryChild, Integer idStatus, Integer idGender, Float minPrice, Float maxPrice,Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on p1.idProduct = pd.idProduct " +
                    " join Category c on c.idCategory = pd.idCategory " +
                    " where p1.idStatus = :idStatus " +
                    " and pd.idStatus = 2 " +
                    " and c.parentId = :idCategoryParent)"
    )
    Page<Product> findAllByIdStatusAndIdCategoryParent(Integer idCategoryParent, Integer idStatus, Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idProduct in "+
            "(select distinct(p1.idProduct) from Product p1 " +
                    " join ProductDetail pd on p1.idProduct = pd.idProduct " +
                    " join Category c on c.idCategory = pd.idCategory " +
                    " where p1.idStatus = :idStatus" +
                    " and pd.idGender = :idGender " +
                    " and pd.idStatus = 2 " +
                    " and c.parentId = :idCategoryParent)"
    )
    Page<Product> findAllByIdGenderAndIdStatusAndIdCategoryParent(Integer idGender, Integer idStatus, Integer idCategoryParent, Pageable pageable);

    Page<Product> findAllByNameProductLikeAndIdStatus(String name, Integer idStatus ,Pageable pageable);

    @Query(
            "select p from Product p " +
                    " where p.idStatus = 2 " +
                    " order by p.dateCreate desc"
    )
    Page<Product> findAllProductNew(Pageable pageable);

    @Query(
            value = "select * from _Product p where "+
            " p.idStatus = 2 "+
            " order by (select sum(b.quantity) as total from _Bill_Product b "+
            " join _Product_Detail pd on pd.idProductDetail = b.idProductDetail"+
            " join _Product p1 on p1.idProduct = pd.idProduct where p1.idProduct = p.idProduct) desc",
            nativeQuery = true
    )
    Page<Product> findAllProductHot(Pageable pageable);

    @Query(
            "select p from Product p where p.idProduct in " +
                    " (select distinct (l.idProduct) from Like l" +
                    " join Product p1 on p1.idProduct = l.idProduct " +
                    " where p1.idStatus = 2 " +
                    " and l.idUser = :userId " +
                    " and l.likeProduct = true )"
    )
    Page<Product> findProductLikeByIdUser(Integer userId, Pageable pageable);

}