package com.localbrand.schedule;

import com.localbrand.common.Tag_Enum;
import com.localbrand.entity.Combo;
import com.localbrand.entity.ComboTag;
import com.localbrand.entity.ProductDetail;
import com.localbrand.entity.ProductTag;
import com.localbrand.repository.ComboRepository;
import com.localbrand.repository.ComboTagRepository;
import com.localbrand.repository.ProductDetailRepository;
import com.localbrand.repository.ProductTagRepository;
import com.localbrand.schedule.base.BaseSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ComboOutOfStockSchedule extends BaseSchedule<Combo> {

    private final ComboRepository comboRepository;
    private final ComboTagRepository comboTagRepository;

    @Override
    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        super.baseExecute();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    protected void processItems(List<Combo> scheduleItems) {
        List<Integer> lstComboId = scheduleItems.stream().map(
                combo -> {
                    return combo.getIdCombo().intValue();
                }
        ).collect(Collectors.toList());

        List<ComboTag> comboTagList = this.comboTagRepository.findAllByIdTagCombo(lstComboId, Tag_Enum.OUT_OF_STOCK.getCode());

        List<ComboTag> newComboTag = new ArrayList<>();

        scheduleItems.forEach(combo -> {
            boolean check = false;
            for (ComboTag comboTag:comboTagList) {
                if(comboTag.getIdCombo().equals(combo.getIdCombo().intValue())){
                    check = true;
                    break;
                }
            }

            if(!check){
                ComboTag comboTag = ComboTag.builder()
                        .idCombo(combo.getIdCombo().intValue())
                        .idTag(Tag_Enum.OUT_OF_STOCK.getCode())
                        .build();
                newComboTag.add(comboTag);
            }
        });

        if(!newComboTag.isEmpty()){
            this.comboTagRepository.saveAll(newComboTag);
        }

        List<ComboTag> presentComboTag = this.comboTagRepository.findAllByIdTag(Tag_Enum.OUT_OF_STOCK.getCode());

        List<ComboTag> deleteComboTagList = new ArrayList<>();
        presentComboTag.forEach(comboTag -> {
            boolean check = false;
            for (Combo combo: scheduleItems){
                if(comboTag.getIdCombo().equals(combo.getIdCombo().intValue())){
                    check = true;
                    break;
                }
            }
            if(!check){
                deleteComboTagList.add(comboTag);
            }
        });

        this.comboTagRepository.deleteAll(deleteComboTagList);
    }

    @Override
    protected String name() {
        return "Update tag out of stock combo";
    }

    @Override
    protected List<Combo> fetchScheduleItem() {
        return this.comboRepository.findAllByQuantity();
    }
}
