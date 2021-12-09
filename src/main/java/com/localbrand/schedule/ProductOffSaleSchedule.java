package com.localbrand.schedule;

import com.localbrand.common.Status_Enum;
import com.localbrand.common.Tag_Enum;
import com.localbrand.entity.ProductSale;
import com.localbrand.entity.ProductTag;
import com.localbrand.repository.ProductDetailRepository;
import com.localbrand.repository.ProductSaleRepository;
import com.localbrand.repository.ProductTagRepository;
import com.localbrand.schedule.base.BaseSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductOffSaleSchedule extends BaseSchedule<ProductSale> {

    private final ProductSaleRepository productSaleRepository;
    private final ProductTagRepository productTagRepository;

    @Override
    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        super.baseExecute();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    protected void processItems(List<ProductSale> scheduleItems) {

        List<ProductSale> updateProductSaleList = new ArrayList<>();
        List<Integer> idProductDetails = new ArrayList<>();
        scheduleItems.forEach(productSale -> {
            productSale.setIdStatus(Status_Enum.DELETE.getCode());
            updateProductSaleList.add(productSale);
            idProductDetails.add(productSale.getIdProductDetail());
        });

        this.productSaleRepository.saveAll(updateProductSaleList);

        List<ProductTag> productTagList = this.productTagRepository.findAllByIdProductDetailAndIdTag(idProductDetails, Tag_Enum.SALE.getCode());

        this.productTagRepository.deleteAll(productTagList);
    }

    @Override
    protected String name() {
        return "Update tag off sale product";
    }

    @Override
    protected List<ProductSale> fetchScheduleItem() {
       return this.productSaleRepository.findAllByDateEndAndIdStatus();
    }
}
