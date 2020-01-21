package com.skyguard.monitor.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : xingrufei
 * create at:  2020-01-21  10:23
 * @description:
 */
public class ServiceCounter {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceCounter.class);

    private static SystemInfo systemInfo = new SystemInfo();

    private static ReentrantLock lock = new ReentrantLock();

    public static void addSuccessCount(){

        try {
            lock.lock();
            systemInfo.addSuccessCount();
            systemInfo.addTotalCount();
        }catch (Exception e){
            LOG.error("add count error",e);
        }finally {
            lock.unlock();
        }


    }

    public static void addErrorCount(){

        try {
            lock.lock();
            systemInfo.addErrorCount();
            systemInfo.addTotalCount();
        }catch (Exception e){
            LOG.error("add count error",e);
        }finally {
            lock.unlock();
        }

    }

    public static SystemInfo getSystemInfo(){
        return systemInfo;
    }



}
