package com.localbrand.repository;

import com.localbrand.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;

public interface BillRepository extends JpaRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {

    Page<Bill> findAllByBillType(Integer billType,Pageable pageable);

    Page<Bill> findAllByIdUser(Integer idUser, Pageable pageable);

    @Query(
            "select b from Bill b " +
                    " where b.idUser = :idUser " +
                    " and b.dateCreate between :startDate and :endDate"
    )
    Page<Bill> findAllByIdUser(Integer idUser, Date startDate, Date endDate, Pageable pageable);

    @Query(
            "select b from Bill b " +
                    " where "+
                    " b.dateCreate between :startDate and :endDate"
    )
    Page<Bill> findAllByDate(Date startDate, Date endDate, Pageable pageable);

    @Query(
            "select b from Bill b " +
                    " where b.idStatus = :idStatus " +
                    " and b.idUser = :idUser " +
                    " and b.dateCreate between :startDate and :endDate"
    )
    Page<Bill> findAllByIdUserAndIdStatus(Integer idUser, Integer idStatus, Date startDate, Date endDate,Pageable pageable);

    @Query(
            "select b from Bill b " +
                    " where b.idStatus = :idStatus " +
                    " and b.dateCreate between :startDate and :endDate"
    )
    Page<Bill> findAllByDateAndIdStatus(Integer idStatus, Date startDate, Date endDate,Pageable pageable);

}