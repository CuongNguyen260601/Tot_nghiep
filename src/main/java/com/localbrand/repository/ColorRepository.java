package com.localbrand.repository;

import com.localbrand.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long>, JpaSpecificationExecutor<Color> {
    Page<Color> findAll(Pageable pageable);

    Page<Color> findAllByNameColorLike(String nameColor, Pageable pageable);

    Page<Color> findAllByIdStatus(Integer idStatus, Pageable pageable);

    List<Color> getAllByIdStatus(Integer idStatus);

    @Query(
            "select c from Color c " +
                    " where c.idColor in ( "+
            "select distinct c.idColor from Color c " +
                    " join ProductDetail pd on c.idColor = pd.idColor " +
                    " join Product p on pd.idProduct = p.idProduct " +
                    " where p.idProduct = :idProduct)"
    )
    List<Color> findAllByIdProduct(Long idProduct);
}