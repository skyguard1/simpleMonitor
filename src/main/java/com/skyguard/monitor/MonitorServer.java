package com.skyguard.monitor;

import com.skyguard.monitor.task.ServiceTask;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : xingrufei
 * create at:  2020-01-22  11:03
 * @description:
 */
public class MonitorServer {

    private static ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(8);

    private static AtomicBoolean isRun = new AtomicBoolean(false);

    public static void run(){
        if(!isRun.get()){
            isRun.set(true);
            threadPoolExecutor.scheduleAtFixedRate(new ServiceTask(),100,10000, TimeUnit.SECONDS);
        }
    }


}
