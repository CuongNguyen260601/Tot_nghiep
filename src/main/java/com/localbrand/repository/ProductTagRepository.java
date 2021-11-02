package com.localbrand.repository;

import com.localbrand.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long>, JpaSpecificationExecutor<ProductTag> {

    @Query(
            "select t.idTag from ProductTag t" +
                    " where t.idProductDetail = :idProductDetail"
    )
    List<Integer> listTagByProduct(Integer idProductDetail);

}