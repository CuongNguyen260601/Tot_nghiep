package com.localbrand.schedule.base;

import com.localbrand.schedule.gracefulshd.GracefulShutdown;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
public abstract class BaseSchedule<T>{

    abstract public void execute();

    abstract protected void processItems(List<T> scheduleItems);

    abstract protected String name();

    abstract protected List<T> fetchScheduleItem();

    protected Boolean isCanceled = false;

    protected Long lastScannedId = 0L;

    public Boolean isShuttingDown(){
        if(GracefulShutdown.isShuttingDown()){
            this.isCanceled = true;
            return true;
        }
        return false;
    }

    protected void baseExecute(){
        if(this.isCanceled || this.isShuttingDown()){
            return;
        }

        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        int proccessingItems = 0;
        try {
            List<T> scheduleItems = this.fetchScheduleItem();

            proccessingItems = scheduleItems.size();

            if(proccessingItems < 1){
                lastScannedId = 0L;
                return;
            }

            this.processItems(scheduleItems);
        }catch (Exception e){
            log.error("schedule has exception: "+name()+" "+e);
        }finally {
            if(proccessingItems>0){
                log.info("schedule item: "+name()+" "+proccessingItems);
            }
        }
    }
}
