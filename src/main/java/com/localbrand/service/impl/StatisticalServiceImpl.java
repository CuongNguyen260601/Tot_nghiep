package com.localbrand.service.impl;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.response.statistical.*;
import com.localbrand.repository.BillRepository;
import com.localbrand.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public ServiceResult<StatisticalDTO> findAll(Integer month, Integer year) {

        EntityManager em = emf.createEntityManager();

        Query queryStatus = em.createQuery("select new SummaryStatusBillDTO(b.idStatus,  s.nameStatus, count(b.idBill)) " +
                       "from Bill b join Status s on b.idStatus = s.idStatus " +
                       "where month(b.dateCreate) = :month and year(b.dateCreate) = :year " +
                       "group by b.idStatus,s.nameStatus",  SummaryStatusBillDTO.class);
        queryStatus.setParameter("month", month);
        queryStatus.setParameter("year", year);
        List<SummaryStatusBillDTO> listSummaryStatusBill = queryStatus.getResultList();

///////////////////////////////////

        Query queryUser = em.createQuery("select new TopUserBuyDTO(b.idUser, u.firstName, u.lastName , sum(b.total), count(b.idBill)) " +
                        "from User u join Bill b on b.idUser = u.idUser " +
                        "where month(b.dateSuccess) = :month and year(b.dateSuccess) = :year and b.idStatus = 9 " +
                        "group by b.idUser, u.firstName , u.lastName " , TopUserBuyDTO.class).setMaxResults(5);
        queryUser.setParameter("month", month);
        queryUser.setParameter("year", year);
        List<TopUserBuyDTO> listTopUserBuy = queryUser.getResultList();

///////////////////////////////////

        Query queryProduct = em.createQuery("select new TopProductHotDTO(bp.idProductDetail, p.nameProduct ,sum(bp.quantity)) " +
                        "from Bill b join BillProduct bp on b.idBill = bp.idBill " +
                        "join ProductDetail pd on bp.idProductDetail = pd.idProductDetail " +
                        "join Product p on pd.idProduct = p.idProduct " +
                        "where  b.idStatus = 9 and month(b.dateSuccess) = :month and year(b.dateSuccess) = :year " +
                        "group by bp.idProductDetail ,p.nameProduct  order by sum(bp.quantity) desc", TopProductHotDTO.class).setMaxResults(5);
        queryProduct.setParameter("month", month);
        queryProduct.setParameter("year", year);
        List<TopProductHotDTO> listTopProductHot = queryProduct.getResultList();

///////////////////////////////////

        Query queryCombo = em.createQuery("select new TopComboHotDTO(bc.idCombo ,c.nameCombo , sum(bc.quantity)) " +
                        "from Bill b join BillCombo bc on b.idBill = bc.idBill " +
                        "join Combo c on bc.idCombo = c.idCombo " +
                        "where b.idStatus = 9 and month(b.dateSuccess) = :month and year(b.dateSuccess) = :year " +
                        "group by bc.idCombo ,c.nameCombo  order by sum(bc.quantity) desc", TopComboHotDTO.class).setMaxResults(5);
        queryCombo.setParameter("month", month);
        queryCombo.setParameter("year", year);
        List<TopComboHotDTO> listTopComboHot = queryCombo.getResultList();

///////////////////////////////////

//        Query queryToday = em.createQuery("select new TotalRevenueTodayDTO(sum(b.total) , count(b.idBill)) " +
//                        "from Bill b " +
//                        "where b.dateSuccess = cast(CURRENT_TIMESTAMP as date) and b.idStatus = 9", TotalRevenueTodayDTO.class);
//        TotalRevenueTodayDTO totalRevenueTodayDTO;
//        if(Objects.isNull(queryToday.getSingleResult())){
//            totalRevenueTodayDTO = new TotalRevenueTodayDTO();
//        }else {
//            totalRevenueTodayDTO = (TotalRevenueTodayDTO) queryToday.getSingleResult();
//        }

///////////////////////////////////

        Query queryMonth = em.createQuery("select new TotalRevenueTodayDTO(sum(b.total) , count(b.idBill)) " +
                "from Bill b " +
                "where month(b.dateSuccess) = :month and year(b.dateSuccess) = :year and b.idStatus = 9");
        queryMonth.setParameter("month", month);
        queryMonth.setParameter("year", year);
        TotalRevenueTodayDTO totalRevenueMonthDTO;
        try {
            totalRevenueMonthDTO = (TotalRevenueTodayDTO) queryMonth.getSingleResult();
        }catch (Exception e ){
            totalRevenueMonthDTO = new TotalRevenueTodayDTO();
        }

        StatisticalDTO statisticalDTO = StatisticalDTO
                .builder()
                .summaryStatusBill(listSummaryStatusBill)
                .topUserBuy(listTopUserBuy)
                .topProductHot(listTopProductHot)
                .topComboHot(listTopComboHot)
                .totalRevenueMonth(totalRevenueMonthDTO)
                .build();

        return new ServiceResult<>(HttpStatus.OK, "Ok", statisticalDTO);

    }

    @Override
    public ServiceResult<List<SummaryStatusBillDTO>> findByDate(Integer date, Integer month, Integer year){
        EntityManager em = emf.createEntityManager();

        Query queryStatus = em.createQuery("select new SummaryStatusBillDTO(b.idStatus,  s.nameStatus, count(b.idBill)) " +
                "from Bill b join Status s on b.idStatus = s.idStatus " +
                "where day(b.dateCreate) = :date and month(b.dateCreate) = :month and year(b.dateCreate) = :year " +
                "group by b.idStatus,s.nameStatus",  SummaryStatusBillDTO.class);
        queryStatus.setParameter("date", date);
        queryStatus.setParameter("month", month);
        queryStatus.setParameter("year", year);
        return new ServiceResult<>(HttpStatus.OK, "Success",queryStatus.getResultList());
    }

}
