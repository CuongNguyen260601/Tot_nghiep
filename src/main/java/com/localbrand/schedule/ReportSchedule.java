package com.localbrand.schedule;

import com.localbrand.dto.response.statistical.SummaryStatusBillDTO;
import com.localbrand.schedule.base.BaseSchedule;
import com.localbrand.service.MailService;
import com.localbrand.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportSchedule extends BaseSchedule<SummaryStatusBillDTO> {

    private final MailService mailService;
    private final StatisticalService statisticalService;
    @Override
    @Scheduled(cron = "0 */5 * * * ?")
    public void execute() {
        super.baseExecute();
    }

    @Override
    protected void processItems(List<SummaryStatusBillDTO> scheduleItems) {
        mailService.sendEmailBillByDate(scheduleItems);
    }

    @Override
    protected String name() {
        return null;
    }

    @Override
    protected List<SummaryStatusBillDTO> fetchScheduleItem() {
        Integer date = new Date().getDay();
        Integer month = new Date().getMonth();
        Integer year = new Date().getYear();

        return statisticalService.findByDate(date,month,year).getData();
    }
}
