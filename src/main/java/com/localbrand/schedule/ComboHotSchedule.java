package com.localbrand.schedule;

import com.localbrand.common.Status_Enum;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class ComboHotSchedule extends BaseSchedule<Combo> {

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

        List<ComboTag> presentComboTag = this.comboTagRepository.findAllByIdTag(Tag_Enum.HOT.getCode());

        List<ComboTag> deleteComboTagList = new ArrayList<>();

        presentComboTag.forEach(comboTag -> {

            boolean check = false;

            for (Combo combo:scheduleItems) {
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

        List<ComboTag> newComboTagList = new ArrayList<>();

        scheduleItems.forEach(combo -> {
            boolean check = false;

            for (ComboTag comboTag:presentComboTag) {
                if(comboTag.getIdCombo().equals(combo.getIdCombo().intValue())){
                    check = true;
                    break;
                }
            }
            if(!check){
                newComboTagList.add(ComboTag.builder()
                                .idCombo(combo.getIdCombo().intValue())
                                .idTag(Tag_Enum.HOT.getCode())
                        .build());
            }
        });

        this.comboTagRepository.saveAll(newComboTagList);
    }

    @Override
    protected String name() {
        return "Update tag hot combo";
    }

    @Override
    protected List<Combo> fetchScheduleItem() {
        return this.comboRepository.findAllComboHot(Status_Enum.EXISTS.getCode(), 10);
    }
}
