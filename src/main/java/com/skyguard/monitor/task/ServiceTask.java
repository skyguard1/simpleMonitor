package com.skyguard.monitor.task;

import com.skyguard.monitor.client.EsTransportClient;
import com.skyguard.monitor.trace.ServiceCounter;
import com.skyguard.monitor.trace.SystemInfo;

import java.util.Date;

/**
 * @author : xingrufei
 * create at:  2020-01-22  11:01
 * @description:
 */
public class ServiceTask implements Runnable{


    private EsTransportClient esTransportClient = new EsTransportClient();


    @Override
    public void run() {
        SystemInfo systemInfo = ServiceCounter.getSystemInfo();
        systemInfo.setCurrentTime(new Date());
        esTransportClient.sendSystemInfo(systemInfo);
    }


}
