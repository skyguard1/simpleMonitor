package com.skyguard.monitor.client;

import com.skyguard.monitor.trace.MethodInfo;
import com.skyguard.monitor.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : xingrufei
 * create at:  2020-01-20  18:48
 * @description:
 */
@Component
public class EsTransportClient implements Transport {

    @Autowired
    private EsClient esClient;

    @Override
    public void sendResult(MethodInfo methodInfo) {

        String result = JsonUtil.toJsonString(methodInfo);






    }


}
