package com.skyguard.monitor.client;

import com.skyguard.monitor.trace.MethodInfo;

/**
 * @author : xingrufei
 * create at:  2020-01-20  18:42
 * @description:
 */
public interface Transport {

    public void sendResult(MethodInfo methodInfo);



}
