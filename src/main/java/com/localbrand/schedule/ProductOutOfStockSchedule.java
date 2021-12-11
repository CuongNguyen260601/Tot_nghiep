package com.localbrand.schedule;

import com.localbrand.common.Tag_Enum;
import com.localbrand.entity.ProductDetail;
import com.localbrand.entity.ProductTag;
import com.localbrand.repository.ProductDetailRepository;
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
public class ProductOutOfStockSchedule extends BaseSchedule<ProductDetail> {

    private final ProductDetailRepository productDetailRepository;
    private final ProductTagRepository productTagRepository;

    @Override
    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        super.baseExecute();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    protected void processItems(List<ProductDetail> scheduleItems) {
        List<Integer> lstProductDetailId = scheduleItems.stream().map(
                productDetail -> {
                    return productDetail.getIdProductDetail().intValue();
                }
        ).collect(Collectors.toList());

        List<ProductTag> productTagList = this.productTagRepository.findAllByIdProductDetailAndIdTag(lstProductDetailId, Tag_Enum.OUT_OF_STOCK.getCode());

        List<ProductTag> newProductTag = new ArrayList<>();

        scheduleItems.forEach(productDetail -> {
            boolean check = false;
            for (ProductTag productTag:productTagList) {
                if(productTag.getIdProductDetail().equals(productDetail.getIdProductDetail().intValue())){
                    check = true;
                    break;
                }
            }

            if(!check){
                ProductTag productTag = ProductTag.builder()
                        .idProductDetail(productDetail.getIdProductDetail().intValue())
                        .idTag(Tag_Enum.OUT_OF_STOCK.getCode())
                        .build();
                newProductTag.add(productTag);
            }
        });

        if(!newProductTag.isEmpty()){
            this.productTagRepository.saveAll(newProductTag);
        }

        List<ProductTag> presentProductTag = this.productTagRepository.findAllByIdTag(Tag_Enum.OUT_OF_STOCK.getCode());

        List<ProductTag> deleteProductTagList = new ArrayList<>();
        presentProductTag.forEach(productTag -> {
            boolean check = false;
            for (ProductDetail productDetail: scheduleItems){
                if(productTag.getIdProductDetail().equals(productDetail.getIdProductDetail().intValue())){
                    check = true;
                    break;
                }
            }
            if(!check){
                deleteProductTagList.add(productTag);
            }
        });

        this.productTagRepository.deleteAll(deleteProductTagList);
    }

    @Override
    protected String name() {
        return "Update tag out of stock product";
    }

    @Override
    protected List<ProductDetail> fetchScheduleItem() {
        return this.productDetailRepository.findAllByQuantity();
    }
}
