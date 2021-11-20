package com.localbrand.repository;

import com.localbrand.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Long>, JpaSpecificationExecutor<Size> {
    Page<Size> findAll(Pageable pageable);

    Page<Size> findAllByNameSizeLike(String nameSize, Pageable pageable);

    Page<Size> findAllByIdStatus(Integer idStatus, Pageable pageable);

    List<Size> findAllByIdCategoryAndIdStatus(Integer idCategory, Integer idStatus);

    List<Size> findAllByIdCategory(Integer idCategory);

    Page<Size> findAllByIdStatusAndIdCategory(Integer idStatus, Integer idCategory, Pageable pageable);

    Page<Size> findAllByIdCategory (Integer idCategory, Pageable pageable);

    @Query(
            "select s from Size s " +
                    " where s.idSize in ( "+
            "select distinct s.idSize from Size s " +
                    " join ProductDetail pd on pd.idSize = s.idSize " +
                    " join Product p on p.idProduct = pd.idProduct " +
                    " where p.idProduct = :idProduct " +
                    " and pd.idColor = :idColor " +
                    " and pd.idStatus = 2)"
    )
    List<Size> findAllByIdProductAndIdColor(Long idProduct, Integer idColor);

}