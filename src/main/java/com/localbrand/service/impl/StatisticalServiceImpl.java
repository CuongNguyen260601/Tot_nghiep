package com.localbrand.service.impl;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.response.statistical.*;
import com.localbrand.repository.BillRepository;
import com.localbrand.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {

    @PersistenceUnit
    private EntityManagerFactory emf;
    private final BillRepository billRepository;

    @Override
    public ServiceResult<StatisticalDTO> findAll() {

        EntityManager em = emf.createEntityManager();

        List<SummaryStatusBillDTO> listSummaryStatusBill = (List<SummaryStatusBillDTO>)em
                .createQuery("select new SummaryStatusBillDTO(b.idStatus,  s.nameStatus, count(b.idBill)) " +
                       "from Bill b join Status s on b.idStatus = s.idStatus " +
                       "where month(b.dateCreate) = month(CURRENT_TIMESTAMP) " +
                       "group by b.idStatus,s.nameStatus")
                .getResultList();

        List<TopUserBuyDTO> listTopUserBuy = (List<TopUserBuyDTO>)em
                .createQuery("select new TopUserBuyDTO(b.idUser, u.firstName, u.lastName , sum(b.total), count(b.idBill)) " +
                        "from User u join Bill b on b.idUser = u.idUser " +
                        "where month(b.dateSuccess) = month(CURRENT_TIMESTAMP) and b.idStatus = 9 " +
                        "group by b.idUser, u.firstName , u.lastName ")
                .getResultList();

        List<TopProductHotDTO> listTopProductHot = (List<TopProductHotDTO>)em
                .createQuery("select new TopProductHotDTO(bp.idProductDetail, p.nameProduct ,sum(bp.quantity)) " +
                        "from Bill b join BillProduct bp on b.idBill = bp.idBill " +
                        "join ProductDetail pd on bp.idProductDetail = pd.idProductDetail " +
                        "join Product p on pd.idProduct = p.idProduct " +
                        "where  b.idStatus = 9 and month(b.dateSuccess) = month(CURRENT_TIMESTAMP) " +
                        "group by bp.idProductDetail ,p.nameProduct  order by sum(bp.quantity) desc")
                .getResultList();

        List<TopComboHotDTO> listTopComboHot = (List<TopComboHotDTO>)em
                .createQuery("select new TopComboHotDTO(bc.idCombo ,c.nameCombo , sum(bc.quantity)) " +
                        "from Bill b join BillCombo bc on b.idBill = bc.idBill " +
                        "join Combo c on bc.idCombo = c.idCombo " +
                        "where b.idStatus = 9 and month(b.dateSuccess) = month(CURRENT_TIMESTAMP) " +
                        "group by bc.idCombo ,c.nameCombo  order by sum(bc.quantity) desc")
                .getResultList();

        TotalRevenueTodayDTO totalRevenueTodayDTO = (TotalRevenueTodayDTO) em
                .createQuery("select new TotalRevenueTodayDTO(sum(b.total) , count(b.idBill)) " +
                        "from Bill b " +
                        "where b.dateSuccess = cast(CURRENT_TIMESTAMP as date) and b.idStatus = 9")
                .getSingleResult();

        StatisticalDTO statisticalDTO = StatisticalDTO
                .builder()
                .summaryStatusBill(listSummaryStatusBill)
                .topUserBuy(listTopUserBuy)
                .topProductHot(listTopProductHot)
                .topComboHot(listTopComboHot)
                .totalRevenueToday(totalRevenueTodayDTO)
                .build();

        return new ServiceResult<>(HttpStatus.OK, "Ok", statisticalDTO);


    }

}
