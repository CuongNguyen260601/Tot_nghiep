package com.localbrand.repository;

import com.localbrand.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface CartProductRepository extends JpaRepository<CartProduct, Long>, JpaSpecificationExecutor<CartProduct> {

    @Query(
            nativeQuery = true,
            value = "DELETE FROM _Cart_Product WHERE idCart = (SELECT idCart FROM _Cart WHERE idUser = ?1)"
    )
    void deleteAll(Integer idUser);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM _Cart_Product cp " +
                    " JOIN (" +
                    "    SELECT pd.idProductDetail productDetailId, pd.price, P.nameProduct, C.nameColor, S.nameSize, C2.nameCategory, G.nameGender FROM _Product_Detail pd" +
                    "        JOIN _Color C on pd.idColor = C.idColor" +
                    "        JOIN _Size S on pd.idSize = S.idSize" +
                    "        JOIN _Category C2 on pd.idCategory = C2.idCategory" +
                    "        JOIN _Gender G on pd.idGender = G.idGender" +
                    "        JOIN _Product P on pd.idProduct = P.idProduct" +
                    "    WHERE p.nameProduct LIKE %?1% ESCAPE '&'" +
                    "    ) dp ON dp.productDetailId = cp.idProductDetail" +
                    " JOIN (SELECT * FROM _Cart WHERE idUser = ?2) cart ON cart.idCart = cp.idCart "
    )
    Page<Map<String, Object>> findByName(String productName, Long idUser, Pageable pageable);

}