package com.localbrand.schedule;

import com.localbrand.entity.ProductDetail;
import com.localbrand.schedule.base.BaseSchedule;

import java.util.List;

public class ProductSchedule extends BaseSchedule<ProductDetail> {


    @Override
    public void execute() {

    }

    @Override
    protected void processItems(List<ProductDetail> scheduleItems) {

    }

    @Override
    protected String name() {
        return "Update tag product";
    }

    @Override
    protected List<ProductDetail> fetchScheduleItem() {
        return null;
    }
}
