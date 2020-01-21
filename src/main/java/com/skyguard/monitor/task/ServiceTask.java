package com.skyguard.monitor.task;

import com.skyguard.monitor.client.EsTransportClient;
import com.skyguard.monitor.trace.ServiceCounter;
import com.skyguard.monitor.trace.SystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @author : xingrufei
 * create at:  2020-01-21  10:47
 * @description:
 */
@EnableScheduling
public class ServiceTask {

    @Autowired
    private EsTransportClient esTransportClient;

    @Scheduled(fixedRate = 300000)
    public void runTask(){
        SystemInfo systemInfo = ServiceCounter.getSystemInfo();
        systemInfo.setCurrentTime(new Date());
        esTransportClient.sendSystemInfo(systemInfo);
    }



}
