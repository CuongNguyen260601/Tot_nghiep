package com.localbrand.dto.response.statistical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticalDTO {

    //tổng hợp số bill của từng trạng thái của tháng
    private List<SummaryStatusBillDTO> summaryStatusBill;

    //top 5 user mua nhiều nhất
    private List<TopUserBuyDTO> topUserBuy;

    //top 5 sản phẩm bán chạy
    private List<TopProductHotDTO> topProductHot;

    //top 5 combo bán chạy
    private List<TopComboHotDTO> topComboHot;

    //tổng doanh thu , tổng bill
//    private TotalRevenueTodayDTO totalRevenueToday;

    //tổng doanh thu , tổng bill
    private TotalRevenueTodayDTO totalRevenueMonth;

}
