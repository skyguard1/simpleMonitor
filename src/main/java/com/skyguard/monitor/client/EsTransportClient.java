package com.skyguard.monitor.client;

import com.skyguard.monitor.trace.MethodInfo;
import com.skyguard.monitor.trace.Monitor;
import com.skyguard.monitor.util.JsonUtil;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : xingrufei
 * create at:  2020-01-20  18:48
 * @description:
 */
@Component
public class EsTransportClient implements Transport {

    private static final Logger LOG = LoggerFactory.getLogger(Monitor.class);

    private static final String ES_MONITOR_NAME = "monitor";

    @Autowired
    private Client client;

    @Override
    public void sendResult(MethodInfo methodInfo) {

        try {
            String result = JsonUtil.toJsonString(methodInfo);
            client.prepareIndex(methodInfo.getServiceName(), ES_MONITOR_NAME).setSource(result).get();
        }catch (Exception e){
            LOG.error("set methodInfo error",e);
        }


    }


}
