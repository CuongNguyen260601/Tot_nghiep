package com.localbrand.repository;

import com.localbrand.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    @Query(
            "select distinct t.idTag from Tag t " +
                    " join ProductTag pt on pt.idTag = t.idTag " +
                    " join ProductDetail pd on pt.idProductDetail = pd.idProductDetail " +
                    " join Product p on pd.idProduct = p.idProduct " +
                    " where p.idProduct = :idProduct"
    )
    List<Integer> findByIdProduct(Long idProduct);

    @Query(
            "select distinct t.idTag from Tag t " +
                    " join ProductTag pt on pt.idTag = t.idTag " +
                    " join ProductDetail pd on pt.idProductDetail = pd.idProductDetail " +
                    " join Product p on pd.idProduct = p.idProduct " +
                    " where p.idProduct = :idProduct " +
                    " and pd.idColor = :idColor " +
                    " and pd.idSize = :idSize " +
                    " and pd.idStatus = 2"
    )
    List<Integer> findByIdProductAndIdColorAndIdSize(Long idProduct, Integer idColor, Integer idSize);

    @Query(
            "select distinct t.idTag from Tag t " +
                    " join ProductTag pt on pt.idTag = t.idTag " +
                    " join ProductDetail pd on pt.idProductDetail = pd.idProductDetail " +
                    " where pd.idProductDetail = :idProductDetail"
    )
    List<Integer> findByIdProductDetail(Long idProductDetail);
}