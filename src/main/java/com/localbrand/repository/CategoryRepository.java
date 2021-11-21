package com.localbrand.repository;

import com.localbrand.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Page<Category> findAllByParentIdIsNull(Pageable pageable);

    List<Category> findAllByParentIdIsNull();

    Page<Category> findAllByParentId(Integer parentId, Pageable pageable);

    Page<Category> findAllByParentIdIsNullAndNameCategoryLike(String name, Pageable pageable);

    Page<Category> findAllByParentIdIsNullAndIdStatus(Integer idStatus, Pageable pageable);

    Page<Category> findAllByParentIdAndNameCategoryLike(Integer parentId, String nameCategory, Pageable pageable);

    List<Category> findAllByParentIdAndIdStatus(Integer parentId, Integer idStatus);

    List<Category> findAllByParentId(Integer idStatus);

    Page<Category> findAllByParentIdAndIdStatus(Integer parentId, Integer idStatus, Pageable pageable);

    List<Category> findAllByIdStatusAndParentIdIsNull(Integer idStatus);

    @Query(
            "select c from Category c " +
                    " where c.idCategory in (" +
                    " select distinct c.parentId from Category c " +
                    " join ProductDetail pd on pd.idCategory = c.idCategory " +
                    " join Product p on p.idProduct = pd.idProduct " +
                    " where p.idProduct = :idProduct" +
                    ")"
    )
    Category findCategoryParentByIdProduct(Long idProduct);

    @Query(
            "select c from Category c " +
                    " where c.idCategory in (" +
            "select distinct c.idCategory from Category c " +
                    " join ProductDetail pd on pd.idCategory = c.idCategory " +
                    " join Product p on p.idProduct = pd.idProduct " +
                    " where p.idProduct = :idProduct)"
    )
    Category findCategoryChildByIdProduct(Long idProduct);
}