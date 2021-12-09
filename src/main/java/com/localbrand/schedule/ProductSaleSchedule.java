package com.localbrand.schedule;

import com.localbrand.common.Status_Enum;
import com.localbrand.common.Tag_Enum;
import com.localbrand.entity.ProductDetail;
import com.localbrand.entity.ProductSale;
import com.localbrand.entity.ProductTag;
import com.localbrand.repository.ProductDetailRepository;
import com.localbrand.repository.ProductSaleRepository;
import com.localbrand.repository.ProductTagRepository;
import com.localbrand.schedule.base.BaseSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductSaleSchedule extends BaseSchedule<ProductSale> {

    private final ProductTagRepository productTagRepository;
    private final ProductSaleRepository productSaleRepository;

    @Override
    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        super.baseExecute();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    protected void processItems(List<ProductSale> scheduleItems) {

        List<ProductTag> productTagList = this.productTagRepository.findAllByIdTag(Tag_Enum.SALE.getCode());
        List<ProductTag> deleteTagList = new ArrayList<>();
        productTagList.forEach(productTag -> {

            boolean check = false;
            for (ProductSale productSale:scheduleItems) {
                if(productTag.getIdProductDetail().equals(productSale.getIdProductDetail())){
                    check = true;
                    break;
                }
            }
            if(!check){
                deleteTagList.add(productTag);
            }
        });

        this.productTagRepository.deleteAll(deleteTagList);

        List<ProductTag> newProductTag = new ArrayList<>();
        scheduleItems.forEach(productSale -> {
            boolean check = false;
            for (ProductTag productTag:productTagList) {
                if(productSale.getIdProductDetail().equals(productTag.getIdProductDetail())){
                    check = true;
                    break;
                }
            }

            if(!check){
                newProductTag.add(ProductTag.builder()
                                .idProductDetail(productSale.getIdProductDetail())
                                .idTag(Tag_Enum.SALE.getCode())
                        .build());
            }
        });
        this.productTagRepository.saveAll(newProductTag);
    }

    @Override
    protected String name() {
        return "Update tag sale product";
    }

    @Override
    protected List<ProductSale> fetchScheduleItem() {
       return this.productSaleRepository.findAllByIdStatus(Status_Enum.EXISTS.getCode());
    }
}
