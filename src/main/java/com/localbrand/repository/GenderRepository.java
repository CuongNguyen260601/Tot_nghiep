package com.localbrand.repository;

import com.localbrand.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface GenderRepository extends JpaRepository<Gender, Long>, JpaSpecificationExecutor<Gender> {

    @Query(
            "select g from Gender g " +
                    " where g.idGender in ( "+
            "select distinct g.idGender from Gender g " +
                    " join ProductDetail pd on pd.idGender = g.idGender " +
                    " join Product  p on p.idProduct = pd.idProduct " +
                    " where p.idProduct = :idProduct)"
    )
    Gender findByIdProduct(Long idProduct);
}