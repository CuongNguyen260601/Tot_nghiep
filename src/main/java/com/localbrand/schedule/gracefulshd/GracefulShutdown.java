package com.localbrand.schedule.gracefulshd;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Executor;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private volatile Connector connector;

    private final Long waitingTime;

    private static volatile boolean shuttingdown = false;

    public GracefulShutdown(Long waitingTime){
        this.waitingTime = waitingTime;
    }

    public static Boolean isShuttingDown(){
        return shuttingdown;
    }

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        shuttingdown = true;
        this.connector.pause();
        Executor executor = (Executor) this.connector.getProtocolHandler().getExecutor();
        if(executor instanceof ThreadPoolExecutor){
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                if(!threadPoolExecutor.awaitTermination(waitingTime, TimeUnit.SECONDS)){
                    log.warn("Application was forced to shutdown!");
                    threadPoolExecutor.shutdownNow();
                }
            }catch (InterruptedException ex){
                log.error("Graceful shutdown has exception: " + Arrays.toString(ex.getStackTrace()));
                Thread.currentThread().interrupt();
            }
        }
    }
}
